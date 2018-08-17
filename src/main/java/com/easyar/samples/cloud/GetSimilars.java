package com.easyar.samples.cloud;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class GetSimilars {

    private static final String HOST       = "http://your_uuid.cn1.crs.easyar.com:8888";
    private static final String APP_KEY    = "--here is your crs image space's key--";
    private static final String APP_SECRET = "--here is your crs image space's secret--";

    public static void main(String[] args) throws IOException {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(30, TimeUnit.SECONDS);
        builder.readTimeout(120,TimeUnit.SECONDS);

        OkHttpClient client = builder.build();

        JSONObject params = new JSONObject();
        params.put("image", Base64.getEncoder().encodeToString(
                Files.readAllBytes(Paths.get("test_target_image.jpg"))));

        Auth.signParam(params, APP_KEY, APP_SECRET);
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , params.toString());
        Request request = new Request.Builder()
                .url(HOST+"/similar")
                .post(requestBody)
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
