package com.wust.iot.controller.web;

import com.wust.iot.model.User;
import com.wust.iot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RequestMapping(value = "user")
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "")
    @ResponseBody
    public String index(){
        return "success";
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<User> getUserList(HttpServletRequest request){
        System.out.println("token:" + request.getHeader("token"));
        return userService.findUsersList();
    }

    @RequestMapping(value = "common")
    public String commonPage(){
        return "commonpage";
    }

    @RequestMapping(value = "admin")
    public String adminPage(){
        return "adminpage";
    }
}
