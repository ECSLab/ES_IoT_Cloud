package com.wust.iot.controller.web;

import com.wust.iot.model.Project;
import com.wust.iot.model.User;
import com.wust.iot.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "project")
public class ProjectController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProjectService projectService;

    @GetMapping(value = "")
    public String index(HttpServletRequest request){
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        List<Project> list = projectService.findProjectListById(user.getId());
        logger.info("用户"+user.getId()+"项目个数:"+list.size());
        request.setAttribute("list",list);

        return "project/developerHome";
    }

    @GetMapping(value = "index")
    public String index2(HttpServletRequest request){
        return "redirect:/";
    }

    @GetMapping(value = "createProject")
    public String createProject(HttpServletRequest request){
        return "project/createProject";
    }
}
