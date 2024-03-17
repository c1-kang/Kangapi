package com.kang.kangclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONUtil;
import com.kang.kangclientsdk.model.Test;

import java.util.HashMap;
import java.util.Map;

import static com.kang.kangclientsdk.utils.genSignUtil.genSign;


public class KangApiClient {
    private final String accessKey;

    private final String secretKey;

    private static final String PREFIX_URL = "http://localhost:7012/";

    public KangApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByPost(Test test) {
        String json = JSONUtil.toJsonStr(test);

        // 构建请求
        HttpResponse resp = HttpRequest.post(PREFIX_URL + "name/post02")
                .addHeaders(getHeader(json))
                .body(json)
                .execute();
        return resp.body();
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
