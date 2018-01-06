package com.wust.iot.exception;

import org.springframework.web.bind.MissingServletRequestParameterException;

public class MissingParamterException extends RuntimeException{

    private int code;

    private String message;

    private MissingServletRequestParameterException e;

    public MissingParamterException(MissingServletRequestParameterException e) {
        this.e = e;
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

    public void setMessage(String message) {
        this.message = message;
    }

    public MissingServletRequestParameterException getE() {
        return e;
    }

    public void setE(MissingServletRequestParameterException e) {
        this.e = e;
    }
}
