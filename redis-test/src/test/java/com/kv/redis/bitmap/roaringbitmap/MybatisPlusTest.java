package com.kv.redis.bitmap.roaringbitmap;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.kv.redis.common.service.RedisService;
import com.kv.redis.mybatis.mapper.IdentityGoldDAO;
import com.kv.redis.mybatis.service.IdentityGoldService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MybatisPlusTest.class)
class MybatisPlusTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private RedisService redisService;
    @Autowired
    private IdentityGoldService identityGoldService;
    @Autowired
    private IdentityGoldDAO identityGoldDAO;

    @Test
    public void testBitMap() {

    }

}
