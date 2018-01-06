package com.wust.iot.service.impl;

import com.wust.iot.model.Data;
import com.wust.iot.service.DataService;
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
public class DataServiceImplTest {

    @Autowired
    private DataService dataService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void findDataListByDeviceIdOrderByDesc() throws Exception {
        List<Data> list = dataService.findDataListByDeviceIdOrderByDesc(1);
        logger.info("个数为"+list.size());
        for (Data data : list){
            logger.info(data.toString());
        }
    }

    @Test
    public void findDataListByDeviceId() throws Exception {
        List<Data> list = dataService.findDataListByDeviceId(1);
        logger.info("个数为"+list.size());
        for (Data data : list){
            logger.info(data.toString());
        }
    }

}