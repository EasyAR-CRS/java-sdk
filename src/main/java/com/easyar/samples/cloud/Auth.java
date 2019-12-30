package com.easyar.samples.cloud;

import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;

import java.util.stream.Collectors;

public class Auth {

    private String appId;
    private String apiKey;
    private String apiSecret;
    private String cloudURL = "http://cn1.crs.easyar.com:8888";

    private static String generateSignature(JSONObject jso, String appSecret) {
        String paramStr = jso.keySet().stream()
                .sorted()
                .map(key -> key + String.valueOf(jso.get(key)))
                .collect(Collectors.joining());
        return DigestUtils.sha256Hex(paramStr + appSecret);
    }

    @Deprecated
    /*
    *    Signature for SDK 3.x and lower
    * */
    public static JSONObject signParam(JSONObject param, String appKey, String appSecret) {
        param.put(Common.KEY_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        param.put(Common.KEY_APP_KEY, appKey);
        param.put(Common.KEY_SIGNATURE, generateSignature(param, appSecret));
        return param;
    }

    public static JSONObject signParam(JSONObject param, String appId, String apiKey, String apiSecret) {
        param.put(Common.KEY_TIMESTAMP, String.valueOf(System.currentTimeMillis()));
        param.put(Common.KEY_APPID, appId);
        param.put(Common.KEY_APIKEY, apiKey);
        param.put(Common.KEY_SIGNATURE, generateSignature(param, apiSecret));
        return param;
    }

    public Auth(String appId, String apiKey, String apiSecret) {
        this.appId = appId;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
    }

    public Auth(String appId, String apiKey, String apiSecret, String cloudURL) {
        this.appId = appId;
        this.apiKey = apiKey;
        this.apiSecret = apiSecret;
        this.cloudURL = cloudURL;
    }

    public String getAppId() {
        return appId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public String getApiSecret() {
        return apiSecret;
    }

    public String getCloudURL() {
        return cloudURL;
    }

    public static void main(String[] args) {
        Auth auth   = new Auth("test_app_id", "test_api_key",  "test_api_secret");
        JSONObject param = new JSONObject();
        param.put("name", "java-sdk-test");
        param.put("meta", "AR picture to display with base64 format");
        System.out.println(signParam(param, auth.getAppId(), auth.getApiKey(), auth.getApiSecret()));
    }

}
