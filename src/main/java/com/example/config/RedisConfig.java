package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @ClassName RedisConfig
 * @Description:
 * @Author 刘苏义
 * @Date 2022/12/5 11:38
 * @Version 1.0
 */

@Configuration
public class RedisConfig {
    //从配置文件中获取参数注入
    @Value("${redis.host}")
    private String redisHost;
    @Value("${redis.port}")
    private Integer redisPort;
    @Value("${redis.password}")
    private String redisPassword;

    //配置连接池
    @Bean
    JedisPoolConfig jedisPoolConfig(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(10); //最大连接数
        jedisPoolConfig.setMaxIdle(10); //最大空闲连接数
        return jedisPoolConfig;
    }

    //构造一个连接池对象
    @Bean
    public JedisPool jedis(JedisPoolConfig jedisPoolConfig){
        JedisPool jedisPool = new JedisPool(jedisPoolConfig,redisHost,redisPort,
                30,redisPassword);
        return jedisPool;
    }

}
