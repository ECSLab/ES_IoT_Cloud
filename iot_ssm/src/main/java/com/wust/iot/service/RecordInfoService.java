package com.wust.iot.service;

import com.wust.iot.model.RecordInfo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RecordInfoService {


    /**
     * 查找一个记录
     * @param id
     * @return
     */
    RecordInfo findOneRecordInfo(Integer id);

    /**
     * 查找全部的记录信息
     * @return
     */
    List<RecordInfo> findRecordInfoList();

    /**
     * 插入一条新记录详情
     * @param recordInfo
     * @return
     */
    int insertOneRecordInfo(RecordInfo recordInfo);
}
