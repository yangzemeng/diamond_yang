package com.itheima.springmvc.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户登录Controller
 * <p>Title: UserController</p>
 * <p>Description: </p>
 * <p>Company: www.itcast.cn</p> 
 * @version 1.0
 */
@Controller
public class UserController {
	
	@RequestMapping("/user/showlogin")
	public String showLogin() {
		return "login";
	}

	@RequestMapping("/user/login")
	public String userLogin(String username, String password, HttpSession session) {
		// a)判断用户名密码是否正确
		System.out.println(username);
		System.out.println(password);
		// b)如果正确 想session中写入用户信息
		session.setAttribute("username", username);
		// c)返回登录成功，或者跳转到商品列表
		return "redirect:/item/itemList.action";
	}
}
