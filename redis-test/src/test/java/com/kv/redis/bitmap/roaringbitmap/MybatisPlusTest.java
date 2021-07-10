package com.kv.redis.bitmap.roaringbitmap;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kv.redis.RedisTestApplication;
import com.kv.redis.mybatis.entity.IdentityGoldEntity;
import com.kv.redis.mybatis.mapper.IdentityGoldDAO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisTestApplication.class)
class MybatisPlusTest {
    //
    // @Autowired
    // private RedisTemplate<String, Object> redisTemplate;
    //
    // @Autowired
    // private RedisService redisService;
    // @Autowired
    // private IdentityGoldService identityGoldService;
    @Autowired
    private IdentityGoldDAO identityGoldDAO;

    @Test
    public void testBitMap() {
        final List<IdentityGoldEntity> goldEntities = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            goldEntities.add(new IdentityGoldEntity(Long.valueOf(i), "31123489987678670" + i));
        }
        log.info("》》》》insert ：{}", this.identityGoldDAO.insertBatchSomeColumn(goldEntities));
    }

}
