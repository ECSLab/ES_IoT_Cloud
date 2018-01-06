package com.wust.iot.manager.impl;

import com.wust.iot.manager.TokenManager;
import com.wust.iot.model.Token;
import com.wust.iot.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 通过Redis存储Token和验证Token的实现类
 */
@Component
public class RedisTokenManager implements TokenManager{

    private RedisTemplate redis;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    public void setReids(RedisTemplate redis) {
        this.redis = redis;
        //TODO
    }

    public Token createToken(int userId) throws Exception {
        String t = JwtUtil.createJWT(userId);
        Token token = new Token(userId,t);
        logger.info("token:" + token.getToken());
        //存储至Redis 并设置过期时间
//        redis.boundValueOps(userId).set(token,2, TimeUnit.HOURS);
        return token;
    }

    public boolean checkToken(Token token) {
        //去Redis中验证Token
        //TODO
        return false;
    }

    public Token getToken(String authentication) {
        return null;
    }

    public int deleteToken(long userId) {
        //在Redis中删除Token
        //TODO
        return 0;
    }
}
