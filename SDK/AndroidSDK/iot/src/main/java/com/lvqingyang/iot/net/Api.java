package com.lvqingyang.iot.net;

/**
 * 一句话功能描述
 * 功能详细描述
 *
 * @author Lv Qingyang
 * @date 2017/12/2
 * @email biloba12345@gamil.com
 * @github https://github.com/biloba123
 * @blog https://biloba123.github.io/
 */
public class Api {
    public static final String POST_MESSAGE = "http://192.168.43.23:9000/upload";

    private static final String URL_GET_DATA = "http://47.92.48.100:8099";
    public static final String GET_HISTORY_DATA = URL_GET_DATA+"/iot/sdk/device/data";
    public static final String GET_LAST_DATA = URL_GET_DATA+"/iot/sdk/device/data/latest";
    public static final String GET_DATA_WITH_TIME = URL_GET_DATA+"/iot/sdk/device/data/time";

}
