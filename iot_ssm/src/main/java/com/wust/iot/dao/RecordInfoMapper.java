package com.wust.iot.dao;

import com.wust.iot.model.RecordInfo;

import java.util.List;

public interface RecordInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RecordInfo record);

    int insertSelective(RecordInfo record);

    RecordInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RecordInfo record);

    int updateByPrimaryKey(RecordInfo record);

    List<RecordInfo> selectRecordInfoList();
}