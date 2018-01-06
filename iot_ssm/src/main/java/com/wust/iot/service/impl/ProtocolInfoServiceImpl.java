package com.wust.iot.service.impl;

import com.wust.iot.dao.ProtocolMapper;
import com.wust.iot.model.Protocol;
import com.wust.iot.service.ProtocolInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProtocolInfoServiceImpl implements ProtocolInfoService{

    @Autowired
    private ProtocolMapper protocolMapper;

    public Protocol findOneProtocolInfo(Integer id) {
        return protocolMapper.selectByPrimaryKey(id);
    }

    public List<Protocol> findProtocolInfoList() {
        return protocolMapper.selectProtocolInfoList();
    }

    public int insertOneProtocolInfo(Protocol protocol) {
        protocol.setId(null);
        return protocolMapper.insert(protocol);
    }
}
