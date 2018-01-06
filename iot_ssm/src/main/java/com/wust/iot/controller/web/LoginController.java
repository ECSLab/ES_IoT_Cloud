package com.wust.iot.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "login")
@Controller
public class LoginController {

    @RequestMapping("")
    public String login(){
        return "index/login";
    }

    @RequestMapping("index")
    public String login2(){
        return "redirect:/login";
    }
}
