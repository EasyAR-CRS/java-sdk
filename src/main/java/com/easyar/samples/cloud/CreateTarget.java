package com.easyar.samples.cloud;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class CreateTarget {

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

        //Here is target required info in EasyAR SDK 2.0+
        params.put("type","ImageTarget");
        params.put("name", "java-sdk-test");
        params.put("size", "20");
        params.put("meta", Base64.getEncoder().encodeToString(
                Files.readAllBytes(Paths.get("http://my.com/my-3d-model-example"))));  // This is customerized field to store AR content. e.x.: base64(2D picture) less than 2MB or URL of 3D model Object file

        Auth.signParam(params, APP_KEY, APP_SECRET);

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , params.toString());
        Request request = new Request.Builder()
                .url(HOST+"/targets")
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
