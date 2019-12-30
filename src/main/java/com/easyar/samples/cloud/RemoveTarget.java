package com.easyar.samples.cloud;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class RemoveTarget {

    private static final String TARGET_MGMT_URL = "http://cn1.crs.easyar.com:8888";
    private static final String CRS_APPID       = "--here is your CRS AppId--";
    private static final String API_KEY         = "--here is your API Key--";
    private static final String API_SECRET      = "--here is your API Secret--";
    private static final String TARGET_ID       = "my_targetid";
    /* TO_DEL_IDs lists all the targetIds to be removed
     * must be separated by ","
     */
    private static final String TO_DEL_IDs = "targetId1,targetId2,targetId3";

    public String remove(Auth auth, String targetId) throws IOException {
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(auth.getCloudURL()+"/target/"+targetId+"?"+ Common.toParam(
                        Auth.signParam(new JSONObject(), auth.getAppId(), auth.getApiKey(), auth.getApiSecret())
                ))
                .delete()
                .build();
        return new OkHttpClient.Builder().build().newCall(request).execute().body().string();
    }

    public String removeMultiTargets(Auth auth, String targetIds) throws IOException {
        JSONObject params = new JSONObject().put("targetId", targetIds);
        Auth.signParam(params, auth.getAppId(), auth.getApiKey(), auth.getApiSecret());
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , params.toString());
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(auth.getCloudURL() + "/targets")
                .delete(requestBody)
                .build();
        return new OkHttpClient.Builder().build().newCall(request).execute().body().string();
    }
    public static void main(String[] args) throws IOException{
        Auth accessInfo  =  new Auth(CRS_APPID, API_KEY, API_SECRET, TARGET_MGMT_URL);
        System.out.println(new RemoveTarget().remove(accessInfo, TARGET_ID));
        System.out.println(new RemoveTarget().removeMultiTargets(accessInfo, TO_DEL_IDs));
    }
}
