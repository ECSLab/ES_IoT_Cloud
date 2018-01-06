package com.wust.iot.service.impl;

import com.wust.iot.dao.DeviceMapper;
import com.wust.iot.model.Device;
import com.wust.iot.service.DeviceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeviceInfoServiceImpl implements DeviceInfoService{

    @Autowired
    private DeviceMapper deviceMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());


    public Device findOneDevice(Integer id) {
        return deviceMapper.selectByPrimaryKey(id);
    }

    public List<Device> findDeviceListByProjectId(Integer projectId) {
        return deviceMapper.selectDeviceListByProjectId(projectId);
    }


    public int insertOneDevice(Device device) {
        device.setId(null);
        return deviceMapper.insert(device);
    }

    public int updateOneDevice(Device device) {
        return deviceMapper.updateByPrimaryKey(device);
    }

    public int deleteOneDevice(Integer id) {
        return deviceMapper.deleteByPrimaryKey(id);
    }

    public Device findOneDeviceByApiKeyAndDeviceId(String apiKey, Integer deviceId) {
        logger.debug("service得到的deviceId:" + deviceId);
        return deviceMapper.selectOneDeviceByApiKeyAndDeviceId(apiKey,deviceId);
    }

    public int deleteOneProjectDevices(Integer projectId) {
        return deviceMapper.deleteByProjectId(projectId);
    }

    public List<Device> findDevicesList() {
        return deviceMapper.selectDeviceList();
    }
}
