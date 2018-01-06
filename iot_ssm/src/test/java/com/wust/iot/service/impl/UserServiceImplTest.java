package com.wust.iot.service.impl;

import com.wust.iot.model.User;
import com.wust.iot.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
        "classpath:spring/spring-service.xml"})
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void findOneUserById() throws Exception {
        logger.info(userService.findOneUserById(2).toString());
    }

    @Test
    public void findUsersList() throws Exception {
        List<User> list = userService.findUsersList();

        for (User user : list){
            logger.info(user.toString());
        }

    }

    @Test
    public void insertOneUser() throws Exception {
        User user = new User();
        user.setId(100);
        user.setUsername("test");
        user.setPassword("test");
        user.setWorkPlace("wust");
        user.setBindPhone("789540");
        user.setQq("12345");
        user.setBindEmail("3@qq.com");
        int count = userService.insertOneUser(user);
        logger.info(String.valueOf(count));
    }

    @Test
    public void updateOneUser() throws Exception {
        User user = userService.findOneUserById(3);
        user.setWorkPlace("wust 508");
        user.setRealName("老王");
        userService.updateOneUser(user);
    }

    @Test
    public void deleteOneUser() throws Exception {
        int count = userService.deleteOneUser(100);
        logger.info(String.valueOf(count));
    }

}