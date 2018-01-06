package com.wust.iot.dao;

import com.wust.iot.model.Device;

import java.util.List;

public interface DeviceMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Device record);

    int insertSelective(Device record);

    Device selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Device record);

    int updateByPrimaryKey(Device record);

    List<Device> selectDeviceListByProjectId(Integer projectId);

    Device selectOneDeviceByApiKeyAndDeviceId(String apiKey,Integer deviceId);

    int deleteByProjectId(Integer projectId);

    List<Device> selectDeviceList();
}