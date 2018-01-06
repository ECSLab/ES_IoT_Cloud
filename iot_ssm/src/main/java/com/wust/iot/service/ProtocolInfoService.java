package com.wust.iot.service;

import com.wust.iot.model.Protocol;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ProtocolInfoService {

    /**
     * 查找一个通讯方式
     * @param id
     * @return
     */
    Protocol findOneProtocolInfo(Integer id);

    /**
     * 查找全部的通讯方式
     * @return
     */
    List<Protocol> findProtocolInfoList();

    /**
     * 新增一条通讯方式
     * @param protocol
     * @return
     */
    int insertOneProtocolInfo(Protocol protocol);

}
