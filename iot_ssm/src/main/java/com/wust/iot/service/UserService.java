package com.wust.iot.service;

import com.wust.iot.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface UserService {

    /**
     * 根据id查询用户
     * @return
     */
    User findOneUserById(Integer id);


    /**
     * 查询所用用户
     * @return
     */
    List<User> findUsersList();

    /**
     * 插入新用户
     * @param user
     * @return
     */
    int insertOneUser(User user);

    /**
     * 更新一个用户
     * @param user
     * @return
     */
    int updateOneUser(User user);

    /**
     * 删除一个用户
     * @param id
     * @return
     */
    int deleteOneUser(Integer id);


    /**
     * 通过用户名查询用户
     * @param username
     * @return
     */
    User findUserByUsername (String username);

    /**
     * 通过手机号查询用户
     * @param username
     * @return
     */
    User findUserByMobile(String username);
}
