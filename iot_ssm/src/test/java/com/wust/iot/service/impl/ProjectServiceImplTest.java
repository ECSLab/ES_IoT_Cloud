package com.wust.iot.service.impl;

import com.wust.iot.model.Project;
import com.wust.iot.service.ProjectService;
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
public class ProjectServiceImplTest {
    @Autowired
    private ProjectService projectService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void findOneProject() throws Exception {
        Project project = projectService.findOneProject(1);
        logger.info(project.toString());
    }

    @Test
    public void findProjectList() throws Exception {
        List<Project> list = projectService.findProjectList();
        logger.info("个数为:" + list.size());
        for (Project project : list){
            logger.info(project.toString());
        }
    }

    @Test
    public void insertOneProject() throws Exception {
        Project project = new Project();
        project.setUserId(2);
        project.setName("unit test");
        project.setApiKey("sad324t5dgd3");
        project.setIndustryId(1);
        project.setProfile("这是代码插入测试");
        project.setCreateTime(new Date());
        int count = projectService.insertOneProject(project);
        logger.info("" + count);
    }

    @Test
    public void updateOneProject() throws Exception {
        Project project = projectService.findOneProject(2);
        project.setName("2次测试");
        int count = projectService.updateOneProject(project);
        logger.info("" + count);
    }

    @Test
    public void deleteOneProject() throws Exception {
        int count = projectService.deleteOneProject(2);
        logger.info("" + count);
    }

}