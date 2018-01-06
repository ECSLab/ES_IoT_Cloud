package com.wust.iot.service.impl;

import com.wust.iot.model.ProjectIndustry;
import com.wust.iot.service.ProjectIndustryService;
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
public class ProjectIndustryServiceImplTest {

    @Autowired
    private ProjectIndustryService projectIndustryService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void findOneProjectIndustry() throws Exception {
        ProjectIndustry projectIndustry = projectIndustryService.findOneProjectIndustry(1);
        logger.info(projectIndustry.toString());
    }

    @Test
    public void findProjectIndustryList() throws Exception {
        List<ProjectIndustry> list = projectIndustryService.findProjectIndustryList();
        logger.info("个数为"+list.size());
        for (ProjectIndustry industry : list){
            logger.info(industry.toString());
        }
    }

    @Test
    public void insertOneProjectIndustry() throws Exception {
        ProjectIndustry projectIndustry = new ProjectIndustry();
        projectIndustry.setName("办公设备");
        int count = projectIndustryService.insertOneProjectIndustry(projectIndustry);
        logger.info("成功插入" + count + "个" );
    }

}