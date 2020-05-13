package com.kuang.springbootmybatis.controller;

import com.kuang.springbootmybatis.mapper.UserMapper;
import com.kuang.springbootmybatis.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @RequestMapping("/queryUsers")
    public List<User> queryUsers() {
        List<User> users = userMapper.getUsers();
        users.forEach(System.out::println);
        return users;
    }

    @RequestMapping("/queryUser/{id}")
    public User queryUser(@PathVariable("id")int id){
        User user = userMapper.getUserById(id);
        System.out.println(user);
        return user;
    }

    @RequestMapping("/deleteUser")
    public int deleteUser() {
        int i = userMapper.deleteUser(888);
        System.out.println(i);
        return i;
    }


    @RequestMapping("/add")
    public int addUser() {
        User user = new User();
        user.setId(888);
        user.setUsername("hello");
        int i = userMapper.addUser(user);
        System.out.println(i);
        return i;
    }

    @RequestMapping("/update")
    public int updateUser() {
        User user = new User();
        user.setId(888);
        user.setUsername("hello888");
        int i = userMapper.updateUser(user);
        System.out.println(i);
        return i;
    }

}
