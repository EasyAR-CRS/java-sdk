package com.easyar.samples.cloud;

import org.asynchttpclient.*;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by qinsi on 6/12/16.
 */
public class RemoveTarget {

    private static final String HOST       = "http://my_uuid.cn1.crs.easyar.com:8888";
    private static final String APP_KEY    = "--here is your crs image space's key--";
    private static final String APP_SECRET = "--here is your crs image space's secret--";

    private static final String TARGET_ID  = "my_targetid";

    private static List<Param> toParams(JSONObject jso) {
        return jso.keySet().stream()
                .map(key -> new Param(key, jso.getString(key)))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        AsyncHttpClient client = new DefaultAsyncHttpClient();

        JSONObject params = new JSONObject();
        Auth.signParam(params, APP_KEY, APP_SECRET);

        client.prepareDelete(HOST + "/target/" + TARGET_ID)
                .setQueryParams(toParams(params))
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
