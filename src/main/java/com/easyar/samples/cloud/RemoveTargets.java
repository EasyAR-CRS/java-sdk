package com.easyar.samples.cloud;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class RemoveTargets {

    private static final String HOST       = "http://your_uuid.cn1.crs.easyar.com:8888";
    private static final String APP_KEY    = "--here is your crs image space's key--";
    private static final String APP_SECRET = "--here is your crs image space's secret--";

    // targetIds to be removed
    // must be separated by ","
    private static final String targetIds = "targetId1,targetId2,targetId3";

    public static void main(String[] args) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(120,TimeUnit.SECONDS);

        OkHttpClient client = builder.build();

        JSONObject params = new JSONObject();

        params.put("targetId",targetIds);
        Auth.signParam(params, APP_KEY, APP_SECRET);

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , params.toString());
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(HOST+"/targets")
                .delete(requestBody)
                .build();
        Call call = client.newCall(request);
        try {
            Response response = call.execute();
            System.out.println(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
