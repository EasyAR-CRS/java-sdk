package com.easyar.samples.cloud;

import org.json.JSONObject;

import java.util.Set;

public class Common {
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
