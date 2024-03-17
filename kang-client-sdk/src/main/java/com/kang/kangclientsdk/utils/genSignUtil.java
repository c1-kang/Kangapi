package com.kang.kangclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * 加密工具
 */
public class genSignUtil {

    public static String genSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String str = body + "." + secretKey;
        return md5.digestHex(str);
    }
}
