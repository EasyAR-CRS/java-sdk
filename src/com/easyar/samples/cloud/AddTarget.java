package com.easyar.samples.cloud;

import org.asynchttpclient.AsyncCompletionHandler;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Created by qinsi on 6/12/16.
 */
public class AddTarget {

    private static final String HOST       = "http://my_uuid.cn1.crs.easyar.com:8888";
    private static final String APP_KEY    = "--here is your crs image space's key--";
    private static final String APP_SECRET = "--here is your crs image space's secret--";

    public static void main(String[] args) throws IOException {
        AsyncHttpClient client = new DefaultAsyncHttpClient();

        JSONObject params = new JSONObject();
        params.put("image", Base64.getEncoder().encodeToString(
                Files.readAllBytes(Paths.get("test_target_image.jpg"))));

        //Here is target required info in EasyAR SDK 2.0+
        params.put("type","ImageTarget");
        params.put("name", "java-sdk-test");
        params.put("size", "20");
        params.put("meta", "http://my.com/my-3d-model-example");  // This is customerized field to store AR content. e.x.: base64(2D picture) less than 2MB or URL of 3D model Object file

        Auth.signParam(params, APP_KEY, APP_SECRET);

        client.preparePost(HOST + "/targets/")
                .setBody(params.toString().getBytes())
                .execute(new AsyncCompletionHandler<Void>() {
                    @Override
                    public Void onCompleted(Response response) throws Exception {
                        System.out.println(response.getResponseBody());
                        client.close();
                        return null;
                    }
                });
    }

}
