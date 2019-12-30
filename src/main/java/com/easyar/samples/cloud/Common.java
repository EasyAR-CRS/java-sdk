package com.easyar.samples.cloud;

import org.json.JSONObject;

import java.util.Set;

public class Common {
    public static final String KEY_APIKEY = "apiKey";
    public static final String KEY_APPID = "appId";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_APP_KEY = "appKey";
    public static final String KEY_SIGNATURE = "signature";

    public final static String KEY_RESULT   = "result";
    public final static String KEY_RESULTS  = "results";
    public final static String KEY_TARGETID = "targetId";
    public final static String KEY_GRADE    = "grade";
    public final static String KEY_COUNT    = "count";

    public static final int   MAXIMUM_SIZE     = 2 * 1024 * 1024; // maximum image (also meta) size is 2MB;

    public static String  toParam(JSONObject jso) {
        Set<String>  keys = jso.keySet();
        StringBuffer sb   = new StringBuffer();
        for (String key : keys){
            sb.append(key);
            sb.append("=");
            sb.append(jso.getString(key));
            sb.append("&");
        }
        return sb.substring(0,sb.length()-1);
    }
}
