package com.easyar.samples.cloud;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class Search {

    private static final String ClientEnd_URL = "http://cn1.crs.easyar.com:8080";
    private static final String CRS_APPID     = "--here is your CRS AppId--";
    private static final String API_KEY       = "--here is your API Key--";
    private static final String API_SECRET    = "--here is your API Secret--";
    private static final String IMAGE_PATH    = "test_target_image.jpg";

    public String search(Auth auth, String imgPath) throws IOException {
        final Path mImagePath   = Paths.get(imgPath);
        byte[] image      = Files.readAllBytes(mImagePath);
        if(image.length > Common.MAXIMUM_SIZE) {
            System.err.println("maximum image size is 2MB");
            System.exit(-1);
        }

        JSONObject params = new JSONObject().put("image", Base64.getEncoder().encodeToString(image));
        Auth.signParam(params, auth.getAppId(), auth.getApiKey(), auth.getApiSecret());
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), params.toString());
        Request request = new Request.Builder()
                .url(auth.getCloudURL() + "/search")
                .post(requestBody)
                .build();
        return new OkHttpClient.Builder().readTimeout(120,TimeUnit.SECONDS)
                .build().newCall(request).execute().body().string();
    }

    public static void main(String[] args) throws IOException {
        Auth accessInfo  =  new Auth(CRS_APPID, API_KEY, API_SECRET, ClientEnd_URL);
        System.out.println(new Search().search(accessInfo, IMAGE_PATH));
    }
}