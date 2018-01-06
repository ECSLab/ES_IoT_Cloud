package com.wust.iot.configuration;

import com.wust.iot.dto.Result;
import com.wust.iot.exception.SimpleException;
import com.wust.iot.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@ControllerAdvice
public class ExceptionHandle{

    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) throws Exception{
        if (e instanceof SimpleException){
            //如果是自定义的类型异常
            SimpleException simpleException = (SimpleException) e;
            return ResultUtil.error(simpleException.getCode(),simpleException.getMessage());
        }else {
            //未定义的异常
            logger.error("[系统异常]{}",e);
            return ResultUtil.error(-1,e.getMessage());
        }


    }

}
