package com.kuang.shirospringboot;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.kuang.shirospringboot.mapper.ShiroMapper;
import com.kuang.shirospringboot.pojo.Shiro;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ShiroSpringbootApplicationTests {

    @Autowired
    ShiroMapper shiroMapper;

    @Test
    void contextLoads() {
        QueryWrapper<Shiro> wrapper = new QueryWrapper<>();
        wrapper.eq("username", "rich");
        Shiro shiro = shiroMapper.selectOne(wrapper);
        System.out.println(shiro);

    }

}
