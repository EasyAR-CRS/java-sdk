package com.easyar.samples.cloud;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.json.JSONObject;
import java.io.IOException;

public class ListTargets {

    private static final String TARGET_MGMT_URL = "http://cn1.crs.easyar.com:8888";
    private static final String CRS_APPID       = "--here is your CRS AppId--";
    private static final String API_KEY         = "--here is your API Key--";
    private static final String API_SECRET      = "--here is your API Secret--";

    public String list(Auth auth) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        JSONObject params = new JSONObject();
        Auth.signParam(params, auth.getAppId(), auth.getApiKey(), auth.getApiSecret());
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(auth.getCloudURL() + "/targets/infos?"+ Common.toParam(params))
                .get()
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    public static void main(String[] args) throws IOException{
        Auth accessInfo  =  new Auth(CRS_APPID, API_KEY, API_SECRET, TARGET_MGMT_URL);
        System.out.println(new ListTargets().list(accessInfo));
    }

}
