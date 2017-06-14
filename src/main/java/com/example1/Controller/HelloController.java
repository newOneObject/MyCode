package com.example1.Controller;

import com.example1.Domain.RedisBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2017/5/25.
 */
@RestController
public class HelloController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping("/hello")
    public String index() {
        // 保存字符串
        stringRedisTemplate.opsForValue().set("aaa", "111");
        String string = stringRedisTemplate.opsForValue().get("aaa");
        System.out.println(string);

        // 保存对象
        RedisBean redisBean = new RedisBean("超人", 20);
        redisTemplate.opsForValue().set(redisBean.getUserName(), redisBean);

        redisBean = new RedisBean("蝙蝠侠", 30);
        redisTemplate.opsForValue().set(redisBean.getUserName(), redisBean);

        redisBean = new RedisBean("蜘蛛侠", 40);
        redisTemplate.opsForValue().set(redisBean.getUserName(), redisBean);
        Object object = redisTemplate.opsForValue().get("超人");
        RedisBean redisBean1 = (RedisBean)object;
        System.out.println(redisBean1.getAge().longValue());
        System.out.println(((RedisBean)redisTemplate.opsForValue().get("蝙蝠侠")).getAge().longValue());
        System.out.println(((RedisBean)redisTemplate.opsForValue().get("蜘蛛侠")).getAge().longValue());
        return "Hello MyRedis";
    }
}
