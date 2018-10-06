package com.itheima.springmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String uri = request.getRequestURL().toString();
		if (uri.contains("login")) {
			return true;
		}
		// a)拦截用户请求，判断用户是否登录
		HttpSession session = request.getSession();
		Object username = session.getAttribute("username");
		if (username != null) {
			// b)如果用户已经登录。放行
			return true;
		}
		// c)如果用户未登录，跳转到登录页面。
		response.sendRedirect(request.getContextPath() + "/user/showlogin");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//handle执行之后，返回ModelAndView之前。
		System.out.println("Interceptor1 postHandle......");
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 返回ModelAndView之后。
		//可以在此处理异常
		System.out.println("Interceptor1 afterCompletion......");
	}

	

}
