package com.liuyi.toutiao.util;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class ToutiaoUtil {

    public static String[] IMAGE_FILE_EXT = new String[] {"jpg", "png", "bmp", "jpeg"};

    public static String IMAGE_DIR = "UploadImages/";
    public static String TOUTIAO_DOMAIN= "http://127.0.0.1:8080/";

    public static String getJSONString(int code) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        return json.toJSONString();
    }

    public static String getJSONString(int code, String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", msg);
        return json.toJSONString();
    }

    public static String getJSONString(int code, Map<String, Object> map) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            json.put(entry.getKey(), entry.getValue());
        }
        return json.toJSONString();
    }

    public static boolean isFileAllowed(String fileExt) {
        for(String ext : IMAGE_FILE_EXT) {
            if(fileExt.equals(ext)) return true;
        }
        return false;
    }

}
