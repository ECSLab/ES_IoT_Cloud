package com.wust.iot.service.impl;

import com.wust.iot.dao.ProjectMapper;
import com.wust.iot.model.Project;
import com.wust.iot.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    public Project findOneProject(Integer id) {
        return projectMapper.selectByPrimaryKey(id);
    }

    public List<Project> findProjectList() {
        return projectMapper.selectProjectList();
    }

    public int insertOneProject(Project project) {
        project.setId(null);
        return projectMapper.insert(project);
    }

    public int updateOneProject(Project project) {
        return projectMapper.updateByPrimaryKey(project);
    }

    public int deleteOneProject(Integer id) {
        return projectMapper.deleteByPrimaryKey(id);
    }

    public List<Project> findProjectListByList(Integer id) {
        return projectMapper.selectProjectListByUserId(id);
    }

    public Project findOneProjectJoinIndustry(Integer id) {
        return projectMapper.selectJoinIndustry(id);
    }

    public Project findOneProjectJoinDeviceAndIndustry(Integer id) {
        return projectMapper.selectJoinDeviceAndIndustry(id);
    }

    public Project findOneProjectByIdAndApiKey(Integer id, String apiKey) {
        return projectMapper.selectByIdAndApiKey(id,apiKey);
    }


}
