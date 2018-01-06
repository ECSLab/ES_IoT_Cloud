package com.wust.iot.controller.api;

import com.wust.iot.dto.Result;
import com.wust.iot.enums.ResultEnums;
import com.wust.iot.model.ProjectIndustry;
import com.wust.iot.service.ProjectIndustryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@RequestMapping(value = "api/projectIndustry")
@EnableSwagger2
@Api(value = "projectIndustry", description = "项目的行业接口")
public class ProjectIndustryApiController {

    @Autowired
    private ProjectIndustryService projectIndustryService;

    @GetMapping(value = "/list")
    @ApiOperation(value = "获取全部项目行业列表",notes = "前端和移动端需要记录projectIndustryId")
    public Result<List<ProjectIndustry>> getList(){
        List<ProjectIndustry> list = projectIndustryService.findProjectIndustryList();
        return new Result<List<ProjectIndustry>>(ResultEnums.SUCCESS,list);
    }
}
