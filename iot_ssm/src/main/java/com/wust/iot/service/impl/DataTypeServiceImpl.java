package com.wust.iot.service.impl;

import com.wust.iot.dao.DataTypeMapper;
import com.wust.iot.model.DataType;
import com.wust.iot.service.DataTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DataTypeServiceImpl implements DataTypeService{

    @Autowired
    private DataTypeMapper dataTypeMapper;

    public DataType findOneDataType(Integer id) {
        return dataTypeMapper.selectByPrimaryKey(id);
    }

    public List<DataType> findDataTypeList() {
        return dataTypeMapper.selectDataTypeList();
    }

    public int insertOneDataType(DataType dataType) {
        dataType.setId(null);
        return dataTypeMapper.insert(dataType);
    }

    public int updateOneDataType(DataType dataType) {
        return dataTypeMapper.updateByPrimaryKey(dataType);
    }

    public int deleteOneDataType(Integer id) {
        return dataTypeMapper.deleteByPrimaryKey(id);
    }
}
