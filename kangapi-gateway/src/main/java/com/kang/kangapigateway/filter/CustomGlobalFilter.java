package com.kang.kangapigateway.filter;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.kang.model.entity.Interfaceinfo;
import com.kang.model.entity.User;
import com.kang.service.InnerInterfaceService;
import com.kang.service.InnerUserInterfaceService;
import com.kang.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;

@Component
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    private static final String INTERFACE_HOST = "http://127.0.0.1:7011";

    @DubboReference
    private InnerUserService innerUserService;

    @DubboReference
    private InnerUserInterfaceService innerUserInterfaceService;

    @DubboReference
    private InnerInterfaceService innerInterfaceService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        // 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String id = request.getId();
        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();
        String localAddress = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        String remoteAddress = Objects.requireNonNull(request.getRemoteAddress()).getHostString();
        MultiValueMap<String, String> queryParams = request.getQueryParams();
        HttpHeaders headers = request.getHeaders();
        ServerHttpResponse response = exchange.getResponse();

        log.info("请求唯一ID: " + id);
        log.info("请求路径: " + path);
        log.info("请求来源地址: " + localAddress);
        log.info("请求来源地址: " + remoteAddress);
        log.info("请求参数: " + queryParams);

        // TODO 访问控制（白名单）


        // 鉴权（ak, sk 是否合法）
        String accessKey = headers.getFirst("accessKey");
        String body = headers.getFirst("body");
        String sign = headers.getFirst("sign");
        String timestamp = headers.getFirst("timestamp");
        String nonce = headers.getFirst("nonce");

        // 时间和当前时间不能超过 5 分钟
        long currentTime = System.currentTimeMillis() / 1000;
        final long FIVE_MINUTES = 60 * 5L;
        assert timestamp != null;
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
            return handleNoAuth(response);
        }
        assert nonce != null;
        if (Long.parseLong(nonce) > 10000L) {
            return handleNoAuth(response);
        }

        // accessKey 是否已分配给用户
        User invokeUser = innerUserService.getInvokeUser(accessKey);
        if (invokeUser == null) {
            return handleNoAuth(response);
        }

        // 接口是否存在
        Interfaceinfo interfaceInfo = innerInterfaceService.getInterfaceInfo(path, method);
        if (interfaceInfo == null) {
            return handleNoAuth(response);
        }

        // 校验 sign 是否正确
        String secretKey = invokeUser.getSecretKey();
        String newSign = genSign(body, secretKey);
        if (!newSign.equals(sign)) {
            return handleNoAuth(response);
        }

        // todo 检查剩余次数

        // 调用 + 1
        Long userID = invokeUser.getId();
        Long interfaceID = interfaceInfo.getId();
        // boolean result = innerUserInterfaceService.invokeCount(userID, interfaceID);

        return handleResponse(exchange, chain, interfaceID, userID);
    }

    /**
     * 处理响应
     *
     * @param exchange    exchange
     * @param chain       责任链
     * @param interfaceID 接口ID
     * @param userID      用户ID
     * @return Mono<Void>
     */
    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain, Long interfaceID, Long userID) {
        try {
            ServerHttpResponse response = exchange.getResponse();

            // 缓存数据的工厂
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            HttpStatusCode statusCode = response.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                ServerHttpResponseDecorator serverHttpResponseDecorator = new ServerHttpResponseDecorator(response) {
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxbody = Flux.from(body);

                            // 往返回值里写数据
                            return super.writeWith(
                                    fluxbody.map(dataBuffer -> {
                                        // 调用成功， 接口次数 + 1
                                        try {
                                            innerUserInterfaceService.invokeCount(userID, interfaceID);
                                        } catch (Exception e) {
                                            log.error("invokeCount error", e);
                                        }

                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);

                                        // 构建日志
                                        // StringBuilder stringBuilder = new StringBuilder(200);
                                        // ArrayList<Object> objects = new ArrayList<>();
                                        // objects.add(response.getStatusCode());
                                        String data = new String(content, StandardCharsets.UTF_8);
                                        // stringBuilder.append(data);

                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return dataBufferFactory.wrap(content);
                                    })
                            );
                        } else {
                            // 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的
                return chain.filter(exchange.mutate().response(serverHttpResponseDecorator).build());
            }
            return chain.filter(exchange); // 降级处理返回数据
        } catch (Exception e) {
            log.error("gateway log exception.\n" + e);
            return chain.filter(exchange);
        }
    }


    private Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public String genSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String str = body + "." + secretKey;
        return md5.digestHex(str);
    }

    @Override
    public int getOrder() {
        return -1;
    }
}

