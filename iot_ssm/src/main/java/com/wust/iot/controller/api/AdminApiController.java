package com.wust.iot.controller.api;

import com.wust.iot.dto.Result;
import com.wust.iot.enums.ResultEnums;
import com.wust.iot.model.Device;
import com.wust.iot.model.Project;
import com.wust.iot.service.DeviceInfoService;
import com.wust.iot.service.ProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@RequestMapping(value = "api/admin")
@EnableSwagger2
@Api(value = "dataType", description = "物联网设备开发者测试接口")
public class AdminApiController {


    @Autowired
    private ProjectService projectService;

    @Autowired
    private DeviceInfoService deviceInfoService;

    @GetMapping(value = "allProject/info")
    @ApiOperation(value = "获取全部项目")
    public Result<List<Project>> getAllProjectsInfo(){
        List<Project> list = projectService.findProjectList();
        return new Result<List<Project>>(ResultEnums.SUCCESS,list);
    }

    @GetMapping(value = "allDevice/info")
    @ApiOperation(value = "获取全部设备")
    public Result<List<Device>> getAllDevicesInfo(){
        List<Device> list = deviceInfoService.findDevicesList();
        return new Result<List<Device>>(ResultEnums.SUCCESS,list);
    }

    @GetMapping(value = "testJrebel")
    @ApiOperation(value = "远程jrebel测试")
    public Result<String> doNothing(){
        return new Result<String>(ResultEnums.SUCCESS);
    }
}
