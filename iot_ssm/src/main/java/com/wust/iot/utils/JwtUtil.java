package com.wust.iot.utils;

import com.wust.iot.enums.ResultEnums;
import com.wust.iot.exception.SimpleException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    public final static String key = "WUST_508$token";

    /**
     * 创建jwt
     *
     * @param userId
     * @return
     * @throws Exception
     */
    public static String createJWT(int userId/*, long ttlMillis*/) throws Exception {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .setSubject("" + userId)
                .signWith(signatureAlgorithm, key);
//        到期时间
        /*if (ttlMillis >= 0){
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }*/
        return builder.compact();
    }


    public static Claims parseJWT(String token){
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (Exception e){
            throw new SimpleException(ResultEnums.TOKEN_NOT_RIGHT);
        }
    }

    public static int getUserIdFromToken(String token) throws Exception {
        Claims claims = parseJWT(token);
        String userId = claims.getSubject().toString();
        return Integer.parseInt(userId);
    }
}
