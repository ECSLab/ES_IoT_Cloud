package com.wust.iot.service;

import com.wust.iot.model.UserRecord;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserRecordService {

    /**
     * 查找一个用户全部的记录
     * @param userId
     * @return
     */
    List<UserRecord> findOneUsersRecordList(Integer userId);

    /**
     * 新增一条用户记录
     * @param userRecord
     * @return
     */
    int insertOneUserRecord(UserRecord userRecord);
}
