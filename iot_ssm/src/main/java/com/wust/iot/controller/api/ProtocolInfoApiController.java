package com.wust.iot.controller.api;

import com.wust.iot.dto.Result;
import com.wust.iot.enums.ResultEnums;
import com.wust.iot.model.Protocol;
import com.wust.iot.service.ProtocolInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

@RestController
@RequestMapping(value = "api/protocolInfo")
@EnableSwagger2
@Api(value = "protocolInfo", description = "设备协议方式接口")
public class ProtocolInfoApiController {

    @Autowired
    private ProtocolInfoService protocolInfoService;

    @ApiOperation(value = "获取设备协议方式列表")
    @GetMapping(value = "/list")
    public Result<List<Protocol>> getList(){
        List<Protocol> list = protocolInfoService.findProtocolInfoList();
        return new Result<List<Protocol>>(ResultEnums.SUCCESS,list);
    }
}
