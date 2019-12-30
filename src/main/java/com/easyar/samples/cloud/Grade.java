package com.easyar.samples.cloud;

import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

public class Grade {
    private static final String TARGET_MGMT_URL = "http://cn1.crs.easyar.com:8888";
    private static final String CRS_APPID       = "--here is your CRS AppId--";
    private static final String API_KEY         = "--here is your API Key--";
    private static final String API_SECRET      = "--here is your API Secret--";
    private static final String IMAGE_PATH      = "test_target_image.jpg";

    enum GradeType {
        DETAIL,
        DETECTION,
        TRACKING
    }
    private static final Map<GradeType, String> GRADE_URL = new HashMap<GradeType, String>(){
        {
            put(GradeType.DETAIL,    "/grade/detail") ;
            put(GradeType.DETECTION, "/grade/detection") ;
            put(GradeType.TRACKING, "/grade/tracking") ;
        }
    };

    public String grade(Auth auth, String imgPath, GradeType gradeType) throws IOException {
        final Path mImagePath   = Paths.get(imgPath);
        JSONObject params = new JSONObject().put("image", Base64.getEncoder().encodeToString(
                Files.readAllBytes(mImagePath)
        ));
        Auth.signParam(params, auth.getAppId(), auth.getApiKey(), auth.getApiSecret());
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8")
                , params.toString());
        Request request = new Request.Builder()
                .url(auth.getCloudURL() + GRADE_URL.get(gradeType))
                .post(requestBody)
                .build();
        return new OkHttpClient.Builder().build().newCall(request).execute().body().string();
    }

    public static void main(String[] args) throws IOException {
        Auth accessInfo  =  new Auth(CRS_APPID, API_KEY, API_SECRET, TARGET_MGMT_URL);

        System.out.println("================== grade details ==================");
        System.out.println(new Grade().grade(accessInfo, IMAGE_PATH, GradeType.DETAIL));

        System.out.println("================== grade for detection ==================");
        JSONObject gradeResp = new JSONObject(new Grade().grade(accessInfo, IMAGE_PATH, GradeType.DETECTION));
        System.out.println("Detection grade: " + gradeResp.getJSONObject(Common.KEY_RESULT).get(Common.KEY_GRADE));

        System.out.println("================== grade for tracking =================== ");
        gradeResp = new JSONObject(new Grade().grade(accessInfo, IMAGE_PATH, GradeType.TRACKING));
        System.out.println("Tracking grade: " + gradeResp.getJSONObject(Common.KEY_RESULT).get(Common.KEY_GRADE));
    }
}
