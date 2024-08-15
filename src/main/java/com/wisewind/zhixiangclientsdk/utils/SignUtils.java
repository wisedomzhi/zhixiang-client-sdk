package com.wisewind.zhixiangclientsdk.utils;

import cn.hutool.crypto.digest.DigestUtil;

public class SignUtils {

    public static String getSign(String body, String secretKey) {
        // 5393554e94bf0eb6436f240a4fd71282
        String sign = DigestUtil.md5Hex(body + secretKey);
        return sign;
    }
}
