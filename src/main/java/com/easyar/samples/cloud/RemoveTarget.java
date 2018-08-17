package com.easyar.samples.cloud;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class RemoveTarget {

    private static final String HOST       = "http://your_uuid.cn1.crs.easyar.com:8888";
    private static final String APP_KEY    = "--here is your crs image space's key--";
    private static final String APP_SECRET = "--here is your crs image space's secret--";

    private static final String TARGET_ID  = "my_targetid";

    public static void main(String[] args) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(120,TimeUnit.SECONDS);

        OkHttpClient client = builder.build();

        JSONObject params = new JSONObject();
        Auth.signParam(params, APP_KEY, APP_SECRET);

        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(HOST+"/target/"+TARGET_ID+"?"+ Common.toParam(params))
                .delete()
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
