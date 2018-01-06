package com.wust.iot.controller.api;

import com.wust.iot.dto.Result;
import com.wust.iot.enums.ResultEnums;
import com.wust.iot.model.DataType;
import com.wust.iot.service.DataTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@RequestMapping(value = "api/dataType")
@EnableSwagger2
@Api(value = "dataType", description = "设备上传数据类型接口")
public class DataTypeApiController {

    @Autowired
    private DataTypeService dataTypeService;

    @ApiOperation(value = "获取全部数据类型",notes = "前端和移动端需要记录dataTypeId")
    @GetMapping(value = "/list")
    public Result<List<DataType>> getDataTypeList(){
        List<DataType> list = dataTypeService.findDataTypeList();
        return new Result<List<DataType>>(ResultEnums.SUCCESS,list);
    }
}
