package com.wust.iot.service.impl;

import com.wust.iot.dao.UserRecordMapper;
import com.wust.iot.model.UserRecord;
import com.wust.iot.service.UserRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRecordServiceImpl implements UserRecordService{

    @Autowired
    private UserRecordMapper userRecordMapper;

    public List<UserRecord> findOneUsersRecordList(Integer userId) {
        return userRecordMapper.selectUserRecordListByUserId(userId);
    }

    public int insertOneUserRecord(UserRecord userRecord) {
        userRecord.setId(null);
        return userRecordMapper.insert(userRecord);
    }
}
