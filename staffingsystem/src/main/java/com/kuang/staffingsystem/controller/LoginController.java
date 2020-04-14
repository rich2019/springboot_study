package com.kuang.staffingsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class LoginController {

    @RequestMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        Model model,
                        HttpSession session) {
        if (username != null && "123".equals(password)) {
            session.setAttribute("username", username);
            return "redirect:/main.html";
//            return "dashboard";
        } else {
            model.addAttribute("msg", "用户名或密码错误");
            return "index";
        }

    }
}
