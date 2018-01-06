package com.wust.iot.utils;

import org.junit.Test;

import static org.junit.Assert.*;

public class JwtUtilTest {
    @Test
    public void createJWT() throws Exception {
        for (int i = 0 ; i < 5; i++){
            System.out.println(JwtUtil.createJWT(100834));
            Thread.sleep(1000);
        }
    }

    @Test
    public void parseJWT() throws Exception {
        String a = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MDk3ODM0NDQsInN1YiI6IjAifQ.GiTmaLYho1Ldy7JNDDyuoy_uQ5PPiyOlIIuLeZ53qb0\n" +
                "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MDk3ODM0NDYsInN1YiI6IjAifQ.eGrE3AeFaBheXReNWXvfKnU7vjJ2-gwROvPU5WTcf1o\n" +
                "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MDk3ODM0NDcsInN1YiI6IjAifQ.jYtP6ldzGiaVUStC7EKQm3kFs_zazIo_1-xsBuh6f3c\n" +
                "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MDk3ODM0NDgsInN1YiI6IjAifQ.2D-hBU7vAvmNdsxR9sQUWRGIHtPobbA5JTgtTALDxhc\n" +
                "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MDk3ODM0NDksInN1YiI6IjAifQ.HLLuoMNt3-0UqheB93-misF7jcyloyVHnbHvBNcn9sc";

        String token = "eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MDk3ODM2MjEsInN1YiI6IjEwMDgzNCJ9.B5DJVoVXXo_bRMS6OwX3C3fOhSuQvRTfp8x9SUiWqak";
        int userId = JwtUtil.getUserIdFromToken(token);
        System.out.println(userId);
    }

}