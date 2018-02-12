package com.wust.iot.controller.api;

import com.wust.iot.dto.ProjectDto;
import com.wust.iot.dto.Result;
import com.wust.iot.enums.RecordEnums;
import com.wust.iot.enums.ResultEnums;
import com.wust.iot.exception.SimpleException;
import com.wust.iot.model.Project;
import com.wust.iot.model.ProjectIndustry;
import com.wust.iot.service.DeviceInfoService;
import com.wust.iot.service.ProjectIndustryService;
import com.wust.iot.service.ProjectService;
import com.wust.iot.service.UserRecordService;
import com.wust.iot.utils.ApiKeyUtil;
import com.wust.iot.utils.UserRecordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/project")
@EnableSwagger2
@Api(value = "project", description = "项目接口")
public class ProjectApiController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectIndustryService projectIndustryService;

    @Autowired
    private UserRecordService userRecordService;

    @Autowired
    private DeviceInfoService deviceInfoService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @ApiOperation(value = "获取某一用户的产品列表")
    @GetMapping(value = "/{userId}/list")
    public Result<List<Project>> getProjectList(@PathVariable(value = "userId") int userId) {
        List<Project> list = projectService.findProjectListById(userId);
        if (list == null)
            return new Result(ResultEnums.SERVER_ERROR);

        return new Result<List<Project>>(ResultEnums.SUCCESS, list);
    }


    @ApiOperation(value = "删除一个项目")
    @DeleteMapping(value = "/delete")
    public Result delete(@RequestParam(value = "userId") int userId,
                         @RequestParam(value = "projectId") int projectId,
                         @RequestParam(value = "apiKey") String apiKey) {
        //项目不存在
        Project project = projectService.findOneProject(projectId);
        if (project == null)
            return new Result(ResultEnums.PROJECT_NOT_EXIST);

        //用户没有此项目
        if (project.getUserId() != userId)
            return new Result(ResultEnums.USER_DO_NOT_HAVE_PROJECT);

        //apiKey不符
        if (!project.getApiKey().equals(apiKey))
            return new Result(ResultEnums.APIKEY_NOT_RIGHT);

        //先删除其下的全部设备并记录
        int count = deviceInfoService.deleteOneProjectDevices(projectId);
        //TODO 待添加记录删除的设备信息(待优化)


        try {
            int r = projectService.deleteOneProject(projectId);
            if (r != 1)
                return new Result(ResultEnums.SERVER_ERROR);
        } catch (Exception e) {
            throw new SimpleException(ResultEnums.SERVER_ERROR, e.getMessage());
        }

        //记录用户行为
        userRecordService.insertOneUserRecord(UserRecordUtil.createUserRecord(userId, RecordEnums.DELETE_PROJECT.id, String.valueOf(projectId)));
        return new Result(ResultEnums.SUCCESS);
    }

    @ApiOperation(value = "创建新项目")
    @PostMapping(value = "/create")
    public Result<Project> createProject(@RequestParam(value = "userId") int userId,
                                         @ModelAttribute(value = "project") ProjectDto createProject) {
        Project project = new Project(
                createProject.getName(),
                createProject.getIndustryId(),
                createProject.getProfile(),
                userId
        );
        project.setCreateTime(new Date());
        project.setApiKey(ApiKeyUtil.create32ApiKey());

        int r = projectService.insertOneProject(project);
        if (r != 1)
            return new Result(ResultEnums.SERVER_ERROR);

        //记录用户行为
        userRecordService.insertOneUserRecord(UserRecordUtil.createUserRecord(userId, RecordEnums.CREATE_PROJECT.id, String.valueOf(project.getId())));
        return new Result<Project>(ResultEnums.SUCCESS, project);
    }

    @ApiOperation(value = "修改项目内容")
    @PutMapping(value = "/modify")
    public Result<ProjectDto> modify(@RequestParam(value = "userId") int userId,
                                     @RequestParam(value = "projectId") int projectId,
                                     @ModelAttribute(value = "project") ProjectDto projectDto) {
        Project oldProject = projectService.findOneProject(projectId);
        if (oldProject.getUserId() != userId)
            return new Result(ResultEnums.USER_DO_NOT_HAVE_PROJECT);

        ProjectIndustry projectIndustry = projectIndustryService.findOneProjectIndustry(projectDto.getIndustryId());
        if (projectIndustry == null)
            return new Result(ResultEnums.INDUSTRY_NOT_EXIST);

        oldProject.setName(projectDto.getName());
        oldProject.setIndustryId(projectDto.getIndustryId());
        oldProject.setProfile(projectDto.getProfile());
        int r = projectService.updateOneProject(oldProject);
        if (r != 1)
            return new Result(ResultEnums.SERVER_ERROR);

        return new Result<ProjectDto>(ResultEnums.SUCCESS, projectDto);
    }

    @ApiOperation(value = "获取某个项目概况")
    @GetMapping(value = "/{projectId}/survey")
    public Result<Project> getProjectSurvey(@PathVariable(value = "projectId") Integer projectId) {
        Project project = projectService.findOneProjectJoinIndustry(projectId);
        return new Result<Project>(ResultEnums.SUCCESS, project);
    }

    @ApiOperation(value = "获取某个项目的详细信息")
    @GetMapping(value = "/{projectId}/detail")
    public Result<Project> getProjectDetail(@PathVariable(value = "projectId") Integer projectId) {
        Project project = projectService.findOneProjectJoinDeviceAndIndustry(projectId);
        project.setDeviceCount(project.getDeviceList().size());
        return new Result<Project>(ResultEnums.SUCCESS, project);
    }
}
