package com.wust.iot.controller.sdk;

import com.wust.iot.dto.Result;
import com.wust.iot.enums.ResultEnums;
import com.wust.iot.model.Data;
import com.wust.iot.model.Device;
import com.wust.iot.service.DataService;
import com.wust.iot.service.DeviceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "sdk/device")
public class DataSDKController {

    @Autowired
    private DataService dataService;

    @Autowired
    private DeviceInfoService deviceService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 查询设备的所有数据(正序,逆序)
     *
     * @param apiKey
     * @param deviceId
     * @param order
     * @return
     */
    @PostMapping(value = "/data")
    public Result<List<Data>> getDeviceData(@RequestParam(value = "apiKey") String apiKey,
                                            @RequestParam(value = "deviceId") String deviceId,
                                            @RequestParam(value = "order", required = false) String order) {

        Device device = deviceService.findOneDeviceByApiKeyAndDeviceId(apiKey, new Integer(deviceId));
        if (device == null) {
            return new Result(ResultEnums.NOT_FOUND_DEVICE);
        }

        List<Data> list = null;
        if (order == null || order.equals("asc"))
            list = dataService.findDataListByDeviceId(device.getId());
        else if (order.equals("desc"))
            list = dataService.findDataListByDeviceIdOrderByDesc(device.getId());
        return new Result<List<Data>>(ResultEnums.SUCCESS, list);
    }

    /**
     * 查询最近一条设备数据
     *
     * @param apiKey
     * @param deviceId
     * @return
     */
    @PostMapping(value = "/data/latest")
    public Result<Data> getDeviceOneDataLatest(@RequestParam(value = "apiKey") String apiKey,
                                               @RequestParam(value = "deviceId") String deviceId) {
        Device device = deviceService.findOneDeviceByApiKeyAndDeviceId(apiKey, new Integer(deviceId));
        if (device == null) {
            return new Result(ResultEnums.NOT_FOUND_DEVICE);
        }

        Data data = dataService.findOneDataLatest(device.getId());
        return new Result<Data>(ResultEnums.SUCCESS, data);
    }

    /**
     * 按照时间段查询数据(正序,逆序)
     *
     * @param apiKey
     * @param deviceId
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping(value = "/data/time")
    public Result<List<Data>> getDeviceDataListDuringTime(@RequestParam(value = "apiKey") String apiKey,
                                                          @RequestParam(value = "deviceId") String deviceId,
                                                          @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                                          @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                                          @RequestParam(value = "order", required = false) String order) {
        Device device = deviceService.findOneDeviceByApiKeyAndDeviceId(apiKey, new Integer(deviceId));
        if (device == null) {
            return new Result(ResultEnums.NOT_FOUND_DEVICE);
        }
        List<Data> list = null;
        if (order == null || order.equals("asc")){
            list = dataService.findDataListByDeviceIdDuringTime(device.getId(), startTime, endTime);
        } else if (order.equals("desc")){
            list = dataService.findDateListByDeviceIdDuringTimeOrderByDesc(device.getId(),startTime,endTime);
        }
        return new Result<List<Data>>(ResultEnums.SUCCESS, list);
    }
}
