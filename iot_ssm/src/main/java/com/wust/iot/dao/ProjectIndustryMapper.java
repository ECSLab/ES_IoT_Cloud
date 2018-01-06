package com.wust.iot.dao;

import com.wust.iot.model.ProjectIndustry;

import java.util.List;

public interface ProjectIndustryMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProjectIndustry record);

    int insertSelective(ProjectIndustry record);

    ProjectIndustry selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProjectIndustry record);

    int updateByPrimaryKey(ProjectIndustry record);

    List<ProjectIndustry> selectProjectIndustryList();
}