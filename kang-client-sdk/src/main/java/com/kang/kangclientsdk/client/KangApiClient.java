package com.kang.kangclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;

import java.util.HashMap;
import java.util.Map;

import static com.kang.kangclientsdk.utils.genSignUtil.genSign;


public class KangApiClient {
    private static final String PREFIX_URL = "http://localhost:7012/";
    private final String accessKey;
    private final String secretKey;

    public KangApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    /**
     * post 请求
     *
     * @param url  接口地址
     * @param json 请求参数
     * @return 响应结果
     */
    public String byPost(String url, String json) {

        // 构建请求
        HttpResponse resp = HttpRequest.post(url)
                .addHeaders(getHeader(json))
                .body(json)
                .execute();
        return resp.body();
    }

    /**
     * get 请求
     *
     * @param url 接口地址
     * @return 结果
     */
    public String byGet(String url) {

        return HttpUtil.get(url);
    }

    private Map<String, String> getHeader(String body) {

        // 请求头里有 accessKey, timestamp, nonce, body, sign
        HashMap<String, String> paramMap = new HashMap<>();
        paramMap.put("accessKey", accessKey);
        paramMap.put("body", body);
        paramMap.put("nonce", RandomUtil.randomNumbers(4));
        paramMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        paramMap.put("sign", genSign(body, secretKey));

        return paramMap;
    }


}
