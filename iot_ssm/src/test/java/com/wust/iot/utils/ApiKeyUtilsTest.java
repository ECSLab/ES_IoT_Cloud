package com.wust.iot.utils;

import org.junit.Test;

public class ApiKeyUtilsTest {

    @Test
    public void create32ApiKey() throws Exception {
        for (int i = 0; i< 100;i++){
            String apiKey = ApiKeyUtil.create32ApiKey();
            System.out.println(apiKey);
        }

    }

}