package com.kuangshen.thymeleaf.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;

@Controller
public class IndexController {

    @RequestMapping("/test")
    public String test(Model model){
        //pom文件中导入thymeleaf依赖,即可使用thymeleaf
        //HTML自动映射到返回值的string中,需要在头中添加 <html lang="en" xmlns:th="http://www.thymeleaf.org">
        model.addAttribute("msg","<h1> hello thymeleaf </h1>");
        model.addAttribute("users", Arrays.asList("a","b"));
        return "test";
    }
}
