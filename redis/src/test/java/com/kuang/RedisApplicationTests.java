package com.kuang;

import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.Set;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void contextLoads() {
        redisTemplate.opsForValue().set("name","rich");
        redisTemplate.opsForValue().set("password","rich");
        System.out.println(redisTemplate.opsForValue().get("name"));
        System.out.println(redisTemplate.opsForValue().get("password"));
    }


    @Test
    void txTest() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.flushDB();

        JSONObject json = new JSONObject();
        json.put("name","tom");
        json.put("age",18);

        Transaction multi = jedis.multi();
        String string = json.toString();
        try {
            multi.set("user1",string);

            int i = 1/0;

            multi.set("user2",string);
            multi.exec();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println(jedis.get("user1"));
            System.out.println(jedis.get("user2"));
            jedis.close();
        }
    }


    @Test
    void jedisTest() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        System.out.println(jedis.ping());

        Set<String> keys = jedis.keys("*");
        keys.forEach(System.out::println);

        jedis.flushDB();

        String set = jedis.set("name", "tom");
        System.out.println(set);
        String name = jedis.get("name");
        System.out.println(name);
    }

}
