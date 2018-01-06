package com.wust.iot.dao;

import com.wust.iot.model.UserRecord;

import java.util.List;

public interface UserRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRecord record);

    int insertSelective(UserRecord record);

    UserRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserRecord record);

    int updateByPrimaryKey(UserRecord record);

    List<UserRecord> selectUserRecordListByUserId(Integer userId);
}