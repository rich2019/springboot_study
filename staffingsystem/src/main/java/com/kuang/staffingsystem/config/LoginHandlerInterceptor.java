package com.kuang.staffingsystem.config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//登陆拦截器
public class LoginHandlerInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object usernameSession = request.getSession().getAttribute("username");
        if (usernameSession != null) {
            return true;
        } else {
            //没有登陆时,session为空,跳转到登陆界面
            request.setAttribute("msg", "请先登陆");
            request.getRequestDispatcher("/index.html").forward(request, response);
            return false;
        }


    }
}
