package com.wust.iot.service;

import com.wust.iot.model.Device;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DeviceInfoService {

    /**
     * 查找一个设备
     * @param id
     * @return
     */
    Device findOneDevice(Integer id);

    /**
     * 查找该项目的全部设备
     * @return
     */
    List<Device> findDeviceListByProjectId(Integer projectId);

    /**
     * 新增一个新的设备
     * @param device
     * @return
     */
    int insertOneDevice(Device device);

    /**
     * 修改一个设备
     * @param device
     * @return
     */
    int updateOneDevice(Device device);

    /**
     * 删除一个设备
     * @param id
     * @return
     */
    int deleteOneDevice(Integer id);

    /**
     * 通过apiKey和number确定一台设备
     * @param apiKey
     * @param deviceId
     * @return
     */
    Device findOneDeviceByApiKeyAndDeviceId(String apiKey,Integer deviceId);

    /**
     * 删除某一项目下的全部设备
     * @param projectId
     * @return
     */
    int deleteOneProjectDevices(Integer projectId);

    /**
     * 获取平台上所有的设备
     * @return
     */
    List<Device> findDevicesList();
}
