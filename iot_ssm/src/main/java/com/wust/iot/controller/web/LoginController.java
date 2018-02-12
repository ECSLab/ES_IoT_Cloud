package com.wust.iot.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping("error")
    @ResponseBody
    public String error(){
        return "授权失败";
    }
}
