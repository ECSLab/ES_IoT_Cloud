package com.wust.iot.utils;

import java.security.MessageDigest;

public class Md5Util {

    /**
     * 获得MD5加密的方法
     *
     * @param password
     * @return
     */
    public static String getMD5ofStr(String password) {
        String resultString = null;
        try {
            //创建MD5算法信息概要
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] results = md.digest(password.getBytes());

            //将得到的字节数组变成字符串返回
            resultString = byteArray2HexStr(results);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     * 处理字节得到MD5密码的方法
     *
     * @param bs
     * @return
     */
    private static String byteArray2HexStr(byte[] bs) {
        StringBuffer sb = new StringBuffer();
        for (byte b : bs) {
            sb.append(byte2HexStr(b));
        }
        return sb.toString();
    }


    /**
     * 字节标准移位转十六进制方法
     */
    private static String byte2HexStr(byte b) {
        String hexStr = null;
        int n = b;
        if (n < 0) {
            // 若需要自定义加密,请修改这个移位算法即可
            n = b & 0x7F + 128;
        }
        hexStr = Integer.toHexString(n / 16) + Integer.toHexString(n % 16);
        return hexStr.toUpperCase();
    }


    /**
     * 提供一个MD5多次加密方法
     */
    public static String getMD5ofStr(String origString, int times) {
        String md5 = getMD5ofStr(origString);
        for (int i = 0; i < times - 1; i++) {
            md5 = getMD5ofStr(md5);
        }
        return getMD5ofStr(md5);
    }

    /**
     * 密码验证方法
     */
    public static boolean verifyPassword(String inputStr, String MD5Code) {
        return getMD5ofStr(inputStr).equals(MD5Code);
    }

    /**
     * 多次加密时的密码验证方法
     */
    public static boolean verifyPassword(String inputStr, String MD5Code,
                                         int times) {
        return getMD5ofStr(inputStr, times).equals(MD5Code);
    }
}
