package com.wust.iot.service.impl;

import com.wust.iot.model.RecordInfo;
import com.wust.iot.service.RecordInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class RecordInfoServiceImplTest {

    @Autowired
    private RecordInfoService recordInfoService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void findOneRecordInfo() throws Exception {
        RecordInfo recordInfo = recordInfoService.findOneRecordInfo(1);
        logger.info(recordInfo.toString());
    }

    @Test
    public void findRecordInfoList() throws Exception {
        List<RecordInfo> list = recordInfoService.findRecordInfoList();
        logger.info("个数:" + list.size());
        for (RecordInfo recordInfo : list){
            logger.info(recordInfo.toString());
        }
    }

    @Test
    public void insertOneRecordInfo() throws Exception {
        RecordInfo recordInfo = new RecordInfo();
        recordInfo.setName("测试用例");
        int count = recordInfoService.insertOneRecordInfo(recordInfo);
        logger.info("" + count);
    }

}