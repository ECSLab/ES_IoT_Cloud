package com.wust.iot.service.impl;

import com.wust.iot.model.Device;
import com.wust.iot.service.DeviceInfoService;
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
public class DeviceInfoServiceImplTest {

    @Autowired
    private DeviceInfoService deviceInfoService;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Test
    public void findOneDevice() throws Exception {
        Device device = deviceInfoService.findOneDevice(1);
        logger.info(device.toString());
    }

    @Test
    public void findDeviceListByProjectId() throws Exception {
        List<Device> list = deviceInfoService.findDeviceListByProjectId(1);
        logger.info("个数为" + list.size());
        for (Device device : list){
            logger.info(device.toString());
        }

    }

    @Test
    public void insertOneDevice() throws Exception {
        Device device = new Device();
        device.setCreateTime(new Date());
        device.setDataType(1);
        device.setName("unit test 新增设备");
        device.setProjectId(1);
        device.setProtocolId(2);
        //number为unique
        device.setNumber("adhfsbsr2");
        device.setPrivacy(0);
        int count = deviceInfoService.insertOneDevice(device);
        logger.info(""+count);
    }

    @Test
    public void updateOneDevice() throws Exception {
        Device device = deviceInfoService.findOneDevice(2);
        device.setName(device.getName() + "  修改测试");
        int count = deviceInfoService.updateOneDevice(device);
        logger.info(""+count);
    }

    @Test
    public void deleteOneDevice() throws Exception {
        int count = deviceInfoService.deleteOneDevice(2);
        logger.info(""+count);
    }

}