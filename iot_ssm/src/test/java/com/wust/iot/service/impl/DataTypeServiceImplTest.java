package com.wust.iot.service.impl;

import com.wust.iot.model.DataType;
import com.wust.iot.service.DataTypeService;
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
public class DataTypeServiceImplTest {

    @Autowired
    private DataTypeService dataTypeService;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @Test
    public void findOneDataType() throws Exception {
        DataType dataType = dataTypeService.findOneDataType(1);
        logger.info(dataType.toString());
    }

    @Test
    public void findDataTypeList() throws Exception {
        List<DataType> list = dataTypeService.findDataTypeList();
        logger.info("个数为"+list.size());
        for (DataType dataType : list){
            logger.info(dataType.toString());
        }
    }

    @Test
    public void insertOneDataType() throws Exception {
        DataType dataType = new DataType();
        dataType.setName("unit test 测试");
        int count = dataTypeService.insertOneDataType(dataType);
        logger.info(""+count);
    }

    @Test
    public void updateOneDataType() throws Exception {
        DataType dataType = dataTypeService.findOneDataType(2);
        dataType.setName(dataType.getName() + "22");
        int count = dataTypeService.updateOneDataType(dataType);
        logger.info(""+count );
    }

    @Test
    public void deleteOneDataType() throws Exception {
        int count = dataTypeService.deleteOneDataType(2);
        logger.info(""+count);
    }

}