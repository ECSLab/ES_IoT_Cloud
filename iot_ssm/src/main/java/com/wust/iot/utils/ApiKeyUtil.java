package com.wust.iot.utils;

import java.util.UUID;

public class ApiKeyUtil {

    public static String create32ApiKey(){
        String apiKey = UUID.randomUUID().toString().replaceAll("-","");
        return apiKey;
    }
}
