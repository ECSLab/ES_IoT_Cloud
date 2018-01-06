package com.wust.iot.dao;

import com.wust.iot.model.Project;

import java.util.List;

public interface ProjectMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Project record);

    int insertSelective(Project record);

    Project selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Project record);

    int updateByPrimaryKey(Project record);

    List<Project> selectProjectList();

    List<Project> selectProjectListByUserId(Integer userId);

    Project selectJoinIndustry(Integer id);

    Project selectJoinDeviceAndIndustry(Integer id);
}