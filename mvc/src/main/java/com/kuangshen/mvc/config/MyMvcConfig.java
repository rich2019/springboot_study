package com.kuangshen.mvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Locale;

//如果你想diy一些定制化功能,只要写这个组件,然后将它交个springboot,springboot就会帮我们自动装配
//如果要扩展springmvc,官方建议我们这么做
@Configuration
@EnableWebMvc
public class MyMvcConfig implements WebMvcConfigurer {

    //视图跳转
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("kuang").setViewName("test");

    }

    //实现了视图解析器的类,我们就可以把它看成视图解析器
    //在dispatcherservlet类中打断点,即可看到myViewResolver
    @Bean
    public ViewResolver myViewResolver() {
        return new MyViewResolver();
    }

    //定义了一个自己的视图解析器
    private class MyViewResolver implements ViewResolver {
        @Override
        public View resolveViewName(String viewName, Locale locale) throws Exception {
            return null;
        }
    }
}
