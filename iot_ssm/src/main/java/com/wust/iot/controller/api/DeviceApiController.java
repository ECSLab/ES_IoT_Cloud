package com.wust.iot.controller.api;

import com.wust.iot.dto.DeviceDto;
import com.wust.iot.dto.Result;
import com.wust.iot.enums.RecordEnums;
import com.wust.iot.enums.ResultEnums;
import com.wust.iot.model.*;
import com.wust.iot.service.*;
import com.wust.iot.utils.UserRecordUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/device")
@EnableSwagger2
@Api(value = "device", description = "设备接口")
public class DeviceApiController {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private DeviceInfoService deviceInfoService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProtocolInfoService protocolInfoService;

    @Autowired
    private DataTypeService dataTypeService;

    @Autowired
    private DataService dataService;

    @Autowired
    private UserRecordService userRecordService;


    @ApiOperation(value = "获取某一用户的某一项目的所有设备")
    @GetMapping(value = "/{userId}/{projectId}/deviceList")
    public Result<List<Device>> getOneUserDeviceList(@PathVariable(value = "userId") int userId,
                                                     @PathVariable(value = "projectId") int projectId) {
        Project project = projectService.findOneProject(projectId);
        if (project.getUserId() != userId)
            return new Result(ResultEnums.USER_DO_NOT_HAVE_PROJECT);

        List<Device> list = deviceInfoService.findDeviceListByProjectId(projectId);
        return new Result<List<Device>>(ResultEnums.SUCCESS, list);
    }

    @ApiOperation(value = "新建设备")
    @PostMapping(value = "/create/device")
    public Result<DeviceDto> createDevice(@RequestParam(value = "userId") int userId,
                                          @ModelAttribute(value = "device") DeviceDto deviceDto) {
        Project project = projectService.findOneProject(deviceDto.getProjectId());

        if (project == null)
            return new Result(ResultEnums.PROJECT_NOT_EXIST);

        if (project.getUserId() != userId)
            return new Result(ResultEnums.USER_DO_NOT_HAVE_PROJECT);

        Protocol protocol = protocolInfoService.findOneProtocolInfo(deviceDto.getProtocolId());
        if (protocol == null)
            return new Result(ResultEnums.PROTOCOL_NOT_EXIST);

        DataType dataType = dataTypeService.findOneDataType(deviceDto.getDataType());
        if (dataType == null)
            return new Result(ResultEnums.DATATYPE_NOT_EXIST);


        Device device = new Device();
        device.setProjectId(deviceDto.getProjectId());
        device.setProtocolId(deviceDto.getProtocolId());
        device.setDataType(deviceDto.getDataType());
        device.setName(deviceDto.getName());
        device.setNumber(deviceDto.getNumber());
        device.setPrivacy(deviceDto.getPrivacy());
        device.setCreateTime(new Date());
        int count = deviceInfoService.insertOneDevice(device);
        if (count != 1)
            return new Result(ResultEnums.SERVER_ERROR);
        else{
            //记录用户行为
            userRecordService.insertOneUserRecord(UserRecordUtil.createUserRecord(userId,RecordEnums.CREATE_DEVICE.id,String.valueOf(device.getId())));
            return new Result<DeviceDto>(ResultEnums.SUCCESS, deviceDto);
        }
    }

    @ApiOperation(value = "获取某个设备的全部数据", notes = "按照时间先后顺序排列")
    @GetMapping(value = "/get/{userId}/device/{deviceId}/data")
    public Result<List<Data>> getDeviceDataList(@PathVariable(value = "userId") int userId,
                                                @PathVariable(value = "deviceId") int deviceId) {
        Device device = deviceInfoService.findOneDevice(deviceId);
        if (device == null)
            return new Result(ResultEnums.DEVICE_NOT_EXIST);

        Project project = projectService.findOneProject(device.getProjectId());
        if (project == null)
            return new Result(ResultEnums.PROJECT_NOT_EXIST);

        if (project.getUserId() != userId)
            return new Result(ResultEnums.USER_DO_NOT_HAVE_PROJECT);

        List<Data> list = dataService.findDataListByDeviceId(deviceId);
        return new Result<List<Data>>(ResultEnums.SUCCESS, list);
    }


    @ApiOperation(value = "获取某个设备的全部数据(逆序)", notes = "按照时间倒序排列")
    @GetMapping(value = "/get/{userId}/device/{deviceId}/dataDesc")
    public Result<List<Data>> getDeviceDataListDesc(@PathVariable(value = "userId") int userId,
                                                    @PathVariable(value = "deviceId") int deviceId) {
        Device device = deviceInfoService.findOneDevice(deviceId);
        if (device == null)
            return new Result(ResultEnums.DEVICE_NOT_EXIST);

        Project project = projectService.findOneProject(device.getProjectId());
        if (project == null)
            return new Result(ResultEnums.PROJECT_NOT_EXIST);

        if (project.getUserId() != userId)
            return new Result(ResultEnums.USER_DO_NOT_HAVE_PROJECT);

        List<Data> list = dataService.findDataListByDeviceIdOrderByDesc(deviceId);
        return new Result<List<Data>>(ResultEnums.SUCCESS, list);
    }


    @ApiOperation(value = "查看某个时间段的设备数据", notes = "按照时间先后顺序排列")
    @PostMapping(value = "/get/device/data/time")
    public Result<List<Data>> getDeviceDataListDuringTime(@RequestParam(value = "userId") int userId,
                                                          @RequestParam(value = "deviceId") int deviceId,
                                                          @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                                          @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {

        Device device = deviceInfoService.findOneDevice(deviceId);
        if (device == null)
            return new Result(ResultEnums.DEVICE_NOT_EXIST);

        Project project = projectService.findOneProject(device.getProjectId());
        if (project == null)
            return new Result(ResultEnums.PROJECT_NOT_EXIST);

        if (project.getUserId() != userId)
            return new Result(ResultEnums.USER_DO_NOT_HAVE_PROJECT);

        List<Data> list = dataService.findDataListByDeviceIdDuringTime(deviceId, startTime, endTime);
        return new Result<List<Data>>(ResultEnums.SUCCESS, list);
    }

    @ApiOperation(value = "查看某个时间段的设备数据(逆序)", notes = "按照时间先后倒序排列")
    @PostMapping(value = "/get/device/data/timeDesc")
    public Result<List<Data>> getDeviceDataListDuringTimeDesc(@RequestParam(value = "userId") int userId,
                                                              @RequestParam(value = "deviceId") int deviceId,
                                                              @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                                              @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime) {

        Device device = deviceInfoService.findOneDevice(deviceId);
        if (device == null)
            return new Result(ResultEnums.DEVICE_NOT_EXIST);

        Project project = projectService.findOneProject(device.getProjectId());
        if (project == null)
            return new Result(ResultEnums.PROJECT_NOT_EXIST);

        if (project.getUserId() != userId)
            return new Result(ResultEnums.USER_DO_NOT_HAVE_PROJECT);

        List<Data> list = dataService.findDateListByDeviceIdDuringTimeOrderByDesc(deviceId, startTime, endTime);
        return new Result<List<Data>>(ResultEnums.SUCCESS, list);
    }

    @ApiOperation(value = "删除某个设备",notes = "谨慎操作，会删除其数据")
    @DeleteMapping(value = "/delete/device")
    public Result deleteDevice(@RequestParam(value = "userId") int userId,
                               @RequestParam(value = "deviceId") int deviceId){

        Device device = deviceInfoService.findOneDevice(deviceId);
        if (device == null)
            return new Result(ResultEnums.DEVICE_NOT_EXIST);

        Project project = projectService.findOneProject(device.getProjectId());
        if (project == null)
            return new Result(ResultEnums.PROJECT_NOT_EXIST);

        if (project.getUserId() != userId)
            return new Result(ResultEnums.USER_DO_NOT_HAVE_PROJECT);

        //删除device
        dataService.deleteOneDeviceDataList(deviceId);
        int count = deviceInfoService.deleteOneDevice(deviceId);
        if (count != 1)
            return new Result(ResultEnums.SERVER_ERROR);

        //记录用户行为
        userRecordService.insertOneUserRecord(UserRecordUtil.createUserRecord(userId, RecordEnums.DELETE_DEVICE.id,String.valueOf(deviceId)));

        return new Result(ResultEnums.SUCCESS);
    }

    @Transactional
    @ApiOperation(value = "修改设备信息")
    @PutMapping(value = "/modify/device")
    public Result<Device> modifyDevice(@RequestParam(value = "userId") int userId,
                               @RequestParam(value = "deviceId") int deviceId,
                               @ModelAttribute(value = "device") DeviceDto deviceDto){
        Device device = deviceInfoService.findOneDevice(deviceId);
        if (device == null)
            return new Result(ResultEnums.DEVICE_NOT_EXIST);

        Project project = projectService.findOneProject(device.getProjectId());
        if (project == null)
            return new Result(ResultEnums.PROJECT_NOT_EXIST);

        if (project.getUserId() != userId)
            return new Result(ResultEnums.USER_DO_NOT_HAVE_PROJECT);

        Protocol protocol = protocolInfoService.findOneProtocolInfo(deviceDto.getProtocolId());
        if (project == null)
            return new Result(ResultEnums.PROTOCOL_NOT_EXIST);

        DataType dataType = dataTypeService.findOneDataType(deviceDto.getDataType());
        if (dataType == null)
            return new Result(ResultEnums.DATATYPE_NOT_EXIST);


        device.setProjectId(deviceDto.getProjectId());
        device.setProtocolId(deviceDto.getProtocolId());
        device.setDataType(deviceDto.getDataType());
        device.setName(deviceDto.getName());
        device.setNumber(deviceDto.getNumber());
        device.setPrivacy(deviceDto.getPrivacy());
        int count = deviceInfoService.updateOneDevice(device);
        if (count != 1)
            return new Result(ResultEnums.SERVER_ERROR);
        return new Result<Device>(ResultEnums.SUCCESS,device);
    }


}
