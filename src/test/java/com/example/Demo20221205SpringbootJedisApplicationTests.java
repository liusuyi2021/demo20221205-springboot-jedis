package com.example;

import javafx.concurrent.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@SpringBootTest
class Demo20221205SpringbootJedisApplicationTests {
    @Autowired
    JedisPool jedisPool;
    @PostConstruct
    void init()
    {
        System.out.println("初始化任务");
        try {  Timer timer = new Timer();
                TimerTask task = new TimerTask() {
                    public void run() {
                        System.out.println("executing now!");
                    }
                };
            timer.schedule(task, 5000, 2000);
        }catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
    }
    @Test
    void contextLoads() {
        //从池子中获取一个连接资源
        Jedis jedis = jedisPool.getResource();
        //通过索引选取数据库
        jedis.select(1);
        String set = jedis.set("name", "liusuyi");
        System.out.println(set);
        //String操作
        String mset = jedis.mset("key1", "value1", "key2", "value2");
        System.out.println(mset);

        //List操作
        Long lpush = jedis.lpush("k3", "k3", "kk3", "kkk3");
        System.out.println(lpush);  //返回最新的数据长度

        //Hash操作
        Map<String,String> map = new HashMap<>();
        map.put("name","张三");
        map.put("age","20");
       // Long key4 = jedis.hset("key4", map);
      //  System.out.println(key4);

        //set 不可重复，没有顺序
        Long key5 = jedis.sadd("key5", "1", "2", "3", "2");
        System.out.println(key5);

        //zset 有序，带score属性排序
        Map map1 = new HashMap();
        map1.put("a",100d);
        map1.put("b",80d);
        map1.put("c",90d);
        Long key6 = jedis.zadd("key6", map1);
        System.out.println(key6);


        //关闭链接，放回池子中
        jedis.close();
    }

}
