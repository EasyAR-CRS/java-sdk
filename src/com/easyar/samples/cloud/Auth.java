package com.easyar.samples.cloud;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

/**
 * Created by qinsi on 6/12/16.
 */
public class Auth {

    private static final String KEY_DATE = "date";
    private static final String KEY_APP_KEY = "appKey";
    private static final String KEY_SIGNATURE = "signature";

    private static String generateSignature(JSONObject jso, String appSecret) {
        String paramStr = jso.keySet().stream()
                .sorted()
                .map(key -> key + jso.getString(key))
                .collect(Collectors.joining());
        return DigestUtils.sha1Hex(paramStr + appSecret);
    }

    public static JSONObject signParam(JSONObject param, String appKey, String appSecret) {
        param.put(KEY_DATE, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now()));
        param.put(KEY_APP_KEY, appKey);
        param.put(KEY_SIGNATURE, generateSignature(param, appSecret));
        return param;
    }

    public static void main(String[] args) {
        final String appKey = "test_app_key";
        final String appSecret = "test_app_secret";

        JSONObject param = new JSONObject();
        param.put("name", "java-sdk-test");
        param.put("meta", "AR picture to display with base64 format");
        param.put(KEY_DATE, "2016-05-27T09:15:39.559Z");
        param.put(KEY_APP_KEY, appKey);
        System.out.println(generateSignature(param, appSecret));

        signParam(param, appKey, appSecret);
        System.out.println(param);
    }

}
