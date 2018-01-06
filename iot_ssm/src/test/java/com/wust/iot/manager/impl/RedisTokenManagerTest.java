package com.wust.iot.manager.impl;

import com.wust.iot.model.Token;
import com.wust.iot.utils.JwtUtil;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;

public class RedisTokenManagerTest {

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Test
    public void createToken() throws Exception {
        int userId = 1;
        String t = JwtUtil.createJWT(userId);
        Token token = new Token(userId,t);
        logger.info("token:" + token.getToken());
    }

}