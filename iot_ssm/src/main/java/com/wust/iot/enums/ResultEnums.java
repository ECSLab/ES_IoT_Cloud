package com.wust.iot.enums;

public enum ResultEnums {
    SUCCESS(0, "成功"),


    USER_NOT_EXIST(100, "用户不存在"),
    PASSWORD_NOT_RIGHT(101, "密码不正确"),
    USERNAME_ALREADY_EXIST(102, "该用户名已存在"),
    TOKEN_PARSER_ERROR(103,"Token解析失败，请确认Token是否正确"),
    TOKEN_NOT_RIGHT(104,"Token不正确"),

    PROJECT_NOT_EXIST(200, "该项目不存在"),
    USER_DO_NOT_HAVE_PROJECT(201, "该用户没有创建此项目"),
    APIKEY_NOT_RIGHT(202, "ApiKey与此项目不符"),
    INDUSTRY_NOT_EXIST(203, "项目的行业id不存在"),
    PROTOCOL_NOT_EXIST(204,"协议id不存在"),
    DATATYPE_NOT_EXIST(205,"数据类型id不存在"),
    DEVICE_NOT_EXIST(206,"设备不存在"),
    NOT_FOUND_DEVICE(207,"未找到相应的项目,请确认APIKEY和deviceId"),


    PARAMETER_INVALID(500, "参数非法"),
    SERVER_ERROR(501, "服务器异常，请联系工作人员"),
    TOKEN_GENERATE_ERROR(502,"Token产生失败");

    private int code;

    private String message;

    ResultEnums(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String msg) {
        this.message = message;
    }
}
