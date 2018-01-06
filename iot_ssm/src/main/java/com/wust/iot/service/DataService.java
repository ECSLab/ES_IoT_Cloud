package com.wust.iot.service;

import com.wust.iot.model.Data;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public interface DataService {

    /**
     * 插入一条数据
     *
     * @param data
     * @return
     */
    int insertOneData(Data data);

    /**
     * 更新一条数据
     *
     * @param data
     * @return
     */
    int updateOneData(Data data);

    /**
     * 查找一条数据
     *
     * @param id
     * @return
     */
    Data findOneData(Integer id);

    /**
     * 删除一条数据
     *
     * @param id
     * @return
     */
    int deleteOneData(Integer id);

    /**
     * 查找一个设备的全部数据(逆序)
     *
     * @param deviceId
     * @return
     */
    List<Data> findDataListByDeviceIdOrderByDesc(Integer deviceId);

    /**
     * 查找一个设备的全部数据(正序)
     *
     * @param deviceId
     * @return
     */
    List<Data> findDataListByDeviceId(Integer deviceId);


    /**
     * 查找一个设备在某个时间段的全部数据（正序）
     * @param deviceId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Data> findDataListByDeviceIdDuringTime(Integer deviceId, Date startTime, Date endTime);


    /**
     * 查找一个设备在某个时间段的全部数据（逆序）
     * @param deviceId
     * @param startTime
     * @param endTime
     * @return
     */
    List<Data> findDateListByDeviceIdDuringTimeOrderByDesc(Integer deviceId, Date startTime, Date endTime);

    /**
     * 删除某个设备的全部数据
     * @param deviceId
     * @return
     */
    int deleteOneDeviceDataList(Integer deviceId);


    Data findOneDataLatest(Integer deviceId);
}
