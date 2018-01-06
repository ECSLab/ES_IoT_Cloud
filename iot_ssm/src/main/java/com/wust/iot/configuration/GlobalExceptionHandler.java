package com.wust.iot.configuration;

import com.wust.iot.enums.ResultEnums;
import com.wust.iot.exception.SimpleException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public SimpleException handleMissingParameterException(MissingServletRequestParameterException e){

        return new SimpleException(ResultEnums.PARAMETER_INVALID,e.getMessage());
    }
}
