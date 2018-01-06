package com.wust.iot.service.impl;

import com.wust.iot.model.Protocol;
import com.wust.iot.service.ProtocolInfoService;
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
public class ProtocolInfoServiceImplTest {

    @Autowired
    private ProtocolInfoService protocolInfoService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void findOneProtocolInfo() throws Exception {
        Protocol protocol = protocolInfoService.findOneProtocolInfo(1);
        logger.info(protocol.toString());
    }

    @Test
    public void findProtocolInfoList() throws Exception {
        List<Protocol> list = protocolInfoService.findProtocolInfoList();
        logger.info("个数为:" + list.size());
        for (Protocol protocol : list){
            logger.info(protocol.toString());
        }
    }

    @Test
    public void insertOneProtocolInfo() throws Exception {
        Protocol protocol = new Protocol();
        protocol.setName("ceshi");
        int count = protocolInfoService.insertOneProtocolInfo(protocol);
        logger.info("" + count);
    }

}