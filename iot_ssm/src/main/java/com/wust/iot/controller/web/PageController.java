package com.wust.iot.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "page")
public class PageController {

    @GetMapping(value = "/login")
    public String loginPage(){
        return "index/login";
    }

    @RequestMapping("")
    public String login(){
        return "index/login";
    }

    @RequestMapping("/index")
    public String testIndex(){
        return "redirect:/";
    }
}
