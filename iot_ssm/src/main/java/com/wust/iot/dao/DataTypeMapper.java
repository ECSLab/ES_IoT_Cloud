package com.wust.iot.dao;

import com.wust.iot.model.DataType;

import java.util.List;

public interface DataTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DataType record);

    int insertSelective(DataType record);

    DataType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DataType record);

    int updateByPrimaryKey(DataType record);

    List<DataType> selectDataTypeList();
}