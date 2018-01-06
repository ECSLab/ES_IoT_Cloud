package com.wust.iot.dao;

import com.wust.iot.model.Protocol;

import java.util.List;

public interface ProtocolMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Protocol record);

    int insertSelective(Protocol record);

    Protocol selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Protocol record);

    int updateByPrimaryKey(Protocol record);

    List<Protocol> selectProtocolInfoList();
}