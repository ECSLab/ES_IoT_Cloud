package com.wust.iot.service;

import com.wust.iot.model.ProjectIndustry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectIndustryService {

    /**
     * 查找一条项目行业
     * @param id
     * @return
     */
    ProjectIndustry findOneProjectIndustry(Integer id);

    /**
     * 查找全部的项目行业
     * @return
     */
    List<ProjectIndustry> findProjectIndustryList();

    /**
     * 新增一条项目行业
     * @param projectIndustry
     * @return
     */
    int insertOneProjectIndustry(ProjectIndustry projectIndustry);
}
