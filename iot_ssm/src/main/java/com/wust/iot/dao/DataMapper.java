package com.wust.iot.dao;

import com.wust.iot.model.Data;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface DataMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Data record);

    int insertSelective(Data record);

    Data selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Data record);

    int updateByPrimaryKey(Data record);

    List<Data> selectListByDeviceIdOrderByDesc(Integer deviceId);

    List<Data> selectListByDeviceId(Integer deviceId);

    List<Data> selectListByDeviceIdDuringTime(@Param("deviceId") Integer deviceId,
                                              @Param("startTime") Date startTime,
                                              @Param("endTime") Date endTime);

    List<Data> selectListByDeviceIdDuringTimeOrderByDesc(@Param("deviceId") Integer deviceId,
                                                         @Param("startTime") Date startTime,
                                                         @Param("endTime") Date endTime);

    int deleteBydeviceId(Integer deviceId);

    Data selectDataLatest(Integer deviceId);
}