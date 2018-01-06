package com.wust.iot.service.impl;

import com.wust.iot.dao.ProjectIndustryMapper;
import com.wust.iot.model.ProjectIndustry;
import com.wust.iot.service.ProjectIndustryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectIndustryServiceImpl implements ProjectIndustryService{

    @Autowired
    private ProjectIndustryMapper projectIndustryMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());


    public ProjectIndustry findOneProjectIndustry(Integer id) {
        return projectIndustryMapper.selectByPrimaryKey(id);
    }

    public List<ProjectIndustry> findProjectIndustryList() {
        return projectIndustryMapper.selectProjectIndustryList();
    }

    public int insertOneProjectIndustry(ProjectIndustry projectIndustry) {
        projectIndustry.setId(null);
        return projectIndustryMapper.insert(projectIndustry);
    }
}
