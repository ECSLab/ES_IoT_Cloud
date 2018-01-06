package com.wust.iot.service.impl;

import com.wust.iot.model.UserRecord;
import com.wust.iot.service.UserRecordService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class UserRecordServiceImplTest {

    @Autowired
    private UserRecordService userRecordService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void findOneUsersRecordList() throws Exception {
        List<UserRecord> list = userRecordService.findOneUsersRecordList(1);
        logger.info("个数:" + list.size());
        for (UserRecord record : list){
            logger.info(record.toString());
        }
    }

    @Test
    public void insertOneUserRecord() throws Exception {
        UserRecord userRecord = new UserRecord();
        userRecord.setUserId(2);
        userRecord.setRecordId(1);
        userRecord.setCreateTime(new Date());
        userRecord.setData("2");
        int count = userRecordService.insertOneUserRecord(userRecord);
        logger.info(String.valueOf(count));
    }

}