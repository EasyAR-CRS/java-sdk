package com.easyar.samples.cloud;

import okhttp3.*;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class CreateTarget {

    private static final String TARGET_MGMT_URL = "http://cn1.crs.easyar.com:8888";
    private static final String CRS_APPID       = "--here is your CRS AppId--";
    private static final String API_KEY         = "--here is your API Key--";
    private static final String API_SECRET      = "--here is your API Secret--";
    private static final String IMAGE_PATH      = "test_target_image.jpg";

    public String create(Auth auth, String imgPath) throws IOException{
        byte[] image = Files.readAllBytes(Paths.get(imgPath));
        if(image.length > Common.MAXIMUM_SIZE) {
            System.err.println("maximum image size is 2MB");
            System.exit(-1);
        }
        JSONObject params = new JSONObject()
                .put("name", "java-sdk-test")
                .put("image", Base64.getEncoder().encodeToString(image))
                .put("type","ImageTarget")
                .put("size", "20")
                .put("meta", "Your customized meta info");
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"),
                Auth.signParam(params, auth.getAppId(), auth.getApiKey(), auth.getApiSecret()).toString());
        Request request = new Request.Builder()
                .url(auth.getCloudURL() + "/targets")
                .post(requestBody)
                .build();
        return new OkHttpClient.Builder().readTimeout(120,TimeUnit.SECONDS).build().newCall(request).execute().body().string();
    }

    public static void main(String[] args) throws IOException {
        Auth accessInfo  =  new Auth(CRS_APPID, API_KEY, API_SECRET, TARGET_MGMT_URL);
        JSONObject createResponse = new JSONObject(new CreateTarget().create(accessInfo, IMAGE_PATH)).getJSONObject(Common.KEY_RESULT);
        System.out.println("created target: "+ createResponse.getString(Common.KEY_TARGETID));
    }
}
