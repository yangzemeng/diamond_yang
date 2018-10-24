package com.baidu.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.dubbo.service.UserService;

/**
 * @author diamod
 * @date 2018-09-06:21:15
 */

@Controller
@RequestMapping("/user")
public class UserController {

    @Reference
    private UserService userService;

    @RequestMapping("/name")
    @ResponseBody
    public String showName(){

       return  userService.getName();
    }
}
