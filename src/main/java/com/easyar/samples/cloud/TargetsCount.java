package com.easyar.samples.cloud;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class TargetsCount {

    private static final String TARGET_MGMT_URL = "http://cn1.crs.easyar.com:8888";
    private static final String CRS_APPID       = "--here is your CRS AppId--";
    private static final String API_KEY         = "--here is your API Key--";
    private static final String API_SECRET      = "--here is your API Secret--";

    public int count(Auth auth) throws IOException {
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(auth.getCloudURL() + "/targets/count?"+Common.toParam(
                        Auth.signParam(new JSONObject(), auth.getAppId(), auth.getApiKey(), auth.getApiSecret())
                ))
                .get()
                .build();
        Response response        = new OkHttpClient.Builder().build().newCall(request).execute();
        JSONObject  responseBody = new JSONObject(response.body().string());
        return responseBody.getJSONObject(Common.KEY_RESULT).getInt(Common.KEY_COUNT);
    }
    public static void main(String[] args) throws IOException {
        Auth accessInfo  =  new Auth(CRS_APPID, API_KEY, API_SECRET, TARGET_MGMT_URL);
        System.out.println("Target number in CRS App Instance: " + new TargetsCount().count(accessInfo) );
    }
}
