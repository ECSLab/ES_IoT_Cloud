package com.wust.iot.service.impl;

import com.wust.iot.dao.UserMapper;
import com.wust.iot.model.User;
import com.wust.iot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    public User findOneUserById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public List<User> findUsersList() {
        return userMapper.selectUserList();
    }

    public int insertOneUser(User user) {
        user.setId(null);
        return userMapper.insert(user);
    }

    public int updateOneUser(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    public int deleteOneUser(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public User findUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    public User findUserByMobile(String mobile) {
        return userMapper.selectUserByMobile(mobile);
    }
}
