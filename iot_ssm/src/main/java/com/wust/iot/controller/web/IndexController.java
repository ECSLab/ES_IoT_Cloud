package com.wust.iot.controller.web;

import com.wust.iot.dao.DeviceMapper;
import com.wust.iot.dao.UserMapper;
import com.wust.iot.model.Device;
import com.wust.iot.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "")
public class IndexController {

    @Autowired
    private DeviceMapper deviceMapper;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @RequestMapping("")
    public String index(){
        return "index/index";
    }

    @RequestMapping("index")
    public String index2(){
        return "redirect:/";
    }




}
