package com.kv.redis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jfilter.EnableJsonFilter;

@EnableAsync
@EnableScheduling
@EnableJsonFilter
@EnableTransactionManagement
@MapperScan(value = {"com.kv.redis.mybatis.mapper"})
@SpringBootApplication(scanBasePackages = {"com.kv"})
public class RedisTestApplication {

    public static void main(final String[] args) {
        SpringApplication.run(RedisTestApplication.class, args);
    }

}
