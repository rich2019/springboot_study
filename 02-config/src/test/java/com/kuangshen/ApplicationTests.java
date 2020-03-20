package com.kuangshen;

import com.kuangshen.pojo.Dog;
import com.kuangshen.pojo.Person;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private Dog dog;
    @Autowired
    private Person person;


    @Test
    void contextLoads() {
        System.out.println(dog);//Dog{name='旺财', age=3}
        System.out.println(person);
        //Person{name='chendong', age=18, happy=true, birth=Sat Aug 18 00:00:00 CST 1888,
        // maps={k1=v1, k2=v2}, lists=[code, music, girl], dog=Dog{name='wangcai', age=5}}
    }

}
