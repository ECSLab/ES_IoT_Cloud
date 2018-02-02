package com.wust.iot.service;

import com.wust.iot.model.Project;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProjectService {

    /**
     * 查找一个项目
     * @param id
     * @return
     */
    Project findOneProject(Integer id);

    /**
     * 列出全部项目
     * @return
     */
    List<Project> findProjectList();

    /**
     * 添加一个新项目
     * @param project
     * @return
     */
    int insertOneProject(Project project);

    /**
     * 修改一个项目
     * @param project
     * @return
     */
    int updateOneProject(Project project);

    /**
     * 删除一个项目
     * @param id
     * @return
     */
    int deleteOneProject(Integer id);

    /**
     * 查找用户的项目列表
     * @param id
     * @return
     */
    List<Project> findProjectListByList(Integer id);

    /**
     * 查询一个项目 关联行业表
     * @param id
     * @return
     */
    Project findOneProjectJoinIndustry(Integer id);

    /**
     * 查询一个项目关联设备表
     * @param id
     * @return
     */
    Project findOneProjectJoinDeviceAndIndustry(Integer id);


    /**
     * 根据id和apiKey查找某一设备
     * @param id
     * @param apiKey
     * @return
     */
    Project findOneProjectByIdAndApiKey(Integer id,String apiKey);
}
