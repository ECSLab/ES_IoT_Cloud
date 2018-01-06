package com.wust.iot.utils;

import com.wust.iot.model.UserRecord;

import java.util.Date;

public class UserRecordUtil {

    public static UserRecord createUserRecord(Integer userId, Integer recordId,String data){
        return new UserRecord(userId,recordId,data,new Date());
    }
}
