package com.wust.iot.exception;

import com.wust.iot.enums.ResultEnums;

public class SimpleException extends RuntimeException{

    private int code;

    public SimpleException(ResultEnums resultEnums) {
        super(resultEnums.getMessage());
        this.code = resultEnums.getCode();
    }

    public SimpleException(ResultEnums resultEnums,String msg){
        super(msg);
        this.code = resultEnums.getCode();
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
