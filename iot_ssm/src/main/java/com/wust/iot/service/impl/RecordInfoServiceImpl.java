package com.wust.iot.service.impl;

import com.wust.iot.dao.RecordInfoMapper;
import com.wust.iot.model.RecordInfo;
import com.wust.iot.service.RecordInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordInfoServiceImpl implements RecordInfoService{

    @Autowired
    private RecordInfoMapper recordInfoMapper;

    public RecordInfo findOneRecordInfo(Integer id) {
        return recordInfoMapper.selectByPrimaryKey(id);
    }

    public List<RecordInfo> findRecordInfoList() {
        return recordInfoMapper.selectRecordInfoList();
    }

    public int insertOneRecordInfo(RecordInfo recordInfo) {
        recordInfo.setId(null);
        return recordInfoMapper.insert(recordInfo);
    }
}
