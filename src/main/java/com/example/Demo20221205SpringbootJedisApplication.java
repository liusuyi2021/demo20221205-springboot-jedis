package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootApplication
public class Demo20221205SpringbootJedisApplication {
    @Autowired
    JedisPool jedisPool;
    @PostConstruct
    void init()
    {
        System.out.println("初始化任务");
        try {  Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                public void run() {
                    //从池子中获取一个连接资源
                    Jedis jedis = jedisPool.getResource();
                    //通过索引选取数据库
                    jedis.select(1);
                    jedis.set("xzx","1");
                    jedis.close();
                }
            };
            timer.schedule(task, 5000, 2000);
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(Demo20221205SpringbootJedisApplication.class, args);

    }

}
