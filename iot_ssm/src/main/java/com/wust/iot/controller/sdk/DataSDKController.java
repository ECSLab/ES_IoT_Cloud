package com.wust.iot.controller.sdk;

import com.wust.iot.dto.PageDto;
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

    private static final int pageSize = 50;

    /**
     * 查询设备的所有数据(正序,逆序) 分页
     *
     * @param apiKey
     * @param deviceId
     * @param order
     * @param pageNumber
     * @return
     */
    @PostMapping(value = "/data")
    public Result<PageDto<Data>> getDeviceData(@RequestParam(value = "apiKey") String apiKey,
                                            @RequestParam(value = "deviceId") String deviceId,
                                            @RequestParam(value = "order", required = false) String order,
                                            @RequestParam(value = "pageNumber", defaultValue = "1") String pageNumber) {

        Device device = deviceService.findOneDeviceByApiKeyAndDeviceId(apiKey, new Integer(deviceId));
        if (device == null) {
            return new Result(ResultEnums.NOT_FOUND_DEVICE);
        }
        PageDto<Data> pageDto = new PageDto<Data>();
        //只有请求第一页时会给总页数
        if (pageNumber.equals("1")){
            int count =  dataService.findDataListCountByDeviceId(device.getId());
            int pageCount = count/pageSize;
            if (count%pageSize!=0)
                pageCount++;
            //设置总页数
            pageDto.setTotalPageNumber(pageCount);
        }

        int pageNum = Integer.parseInt(pageNumber);
        List<Data> list = null;
        if (order == null || order.equals("asc"))
            list = dataService.findDataListByDeviceIdLimit(device.getId(), (pageNum - 1) * pageSize, pageSize);
        else if (order.equals("desc"))
            list = dataService.findDataListByDeviceIdOrderByDescLimit(device.getId(), (pageNum - 1) * pageSize, pageSize);

        //设置当前页数
        pageDto.setCurrentPageNumber(pageNum);
        //设置每一页数据量
        pageDto.setPageSize(pageSize);
        //设置数据
        pageDto.setList(list);
        //设置当前页中数据总量
        pageDto.setCurrentPageDataCount(list.size());
        return new Result<PageDto<Data>>(ResultEnums.SUCCESS, pageDto);
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
     * 按照时间段查询数据(正序,逆序) 分页
     * 总页数，只会在pageNumber=1时查询
     * @param apiKey
     * @param deviceId
     * @param startTime
     * @param endTime
     * @return
     */
    @PostMapping(value = "/data/time")
    public Result<PageDto<Data>> getDeviceDataListDuringTime(@RequestParam(value = "apiKey") String apiKey,
                                                          @RequestParam(value = "deviceId") String deviceId,
                                                          @RequestParam(value = "startTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date startTime,
                                                          @RequestParam(value = "endTime") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date endTime,
                                                          @RequestParam(value = "order", required = false) String order,
                                                          @RequestParam(value = "pageNumber",defaultValue = "1") String pageNumber) {
        Device device = deviceService.findOneDeviceByApiKeyAndDeviceId(apiKey, new Integer(deviceId));
        if (device == null) {
            return new Result(ResultEnums.NOT_FOUND_DEVICE);
        }

        PageDto<Data> pageDto = new PageDto<Data>();
        //只有请求第一页时会给总页数
        if (pageNumber.equals("1")){
            int count =  dataService.findDataListCountByDeviceIdDuringTime(device.getId(), startTime, endTime);
            int pageCount = count/pageSize;
            if (count%pageSize!=0)
                pageCount++;
            //设置总页数
            pageDto.setTotalPageNumber(pageCount);
        }


        int pageNum = Integer.parseInt(pageNumber);
        List<Data> list = null;
        if (order == null || order.equals("asc")) {
            list = dataService.findDataListByDeviceIdDuringTimeLimit(device.getId(), startTime, endTime,(pageNum-1)*pageSize,pageSize);
        } else if (order.equals("desc")) {
            list = dataService.findDataListByDeviceIdDuringTimeOrderByDescLimit(device.getId(), startTime, endTime,(pageNum-1)*pageSize,pageSize);
        }
        //设置当前页数
        pageDto.setCurrentPageNumber(pageNum);
        //设置每一页数据量
        pageDto.setPageSize(pageSize);
        //设置数据
        pageDto.setList(list);
        //设置当前页中数据总量
        pageDto.setCurrentPageDataCount(list.size());
        return new Result<PageDto<Data>>(ResultEnums.SUCCESS, pageDto);
    }
}
