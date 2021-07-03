package com.kv.redis.bitmap.roaringbitmap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.kv.redis.common.service.RedisService;
import com.kv.redis.mybatis.entity.IdentityGoldEntity;
import com.kv.redis.mybatis.mapper.IdentityGoldDAO;
import com.kv.redis.mybatis.service.IdentityGoldService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisTestApplicationTests.class)
class RedisTestApplicationTests {

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

        final ExecutorService executorService =
            new ThreadPoolExecutor(16, 50, 90, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());

        executorService.execute(new Runnable() {
            @Override
            public void run() {
                RedisTestApplicationTests.this.redisTemplate.executePipelined(new RedisCallback<String>() {
                    @Override
                    public String doInRedis(final RedisConnection connection) throws DataAccessException {
                        RedisTestApplicationTests.this.identityGoldDAO.selectList(new LambdaQueryWrapper<IdentityGoldEntity>().last("limit "));
                        for (int i = 0; i < 100; i++) {
                            connection.set(("pipel:" + i).getBytes(), "123".getBytes());
                        }
                        return null;
                    }
                });
            }
        });

    }

    /****
     * 通过pipeline进行批量设置
     * 
     * @param map
     */
    public void setPipe(final Map<String, String> map) {

        final RedisSerializer keySerializer = this.redisTemplate.getKeySerializer();
        final RedisSerializer valueSerializer = this.redisTemplate.getValueSerializer();

        final List list = this.redisTemplate.executePipelined((RedisCallback<String>)connection -> {
            final Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                final Map.Entry<String, String> next = iterator.next();
                connection.set(Objects.requireNonNull(keySerializer.serialize(next.getKey())),
                    Objects.requireNonNull(valueSerializer.serialize(next.getValue())));
            }
            return null;
            // 加不加下面的这一行代码应该都可以
        }, this.redisTemplate.getValueSerializer());

        System.out.println("setPipe" + list);
    }

    // public void getPipe(final String... keys) {
    // final List<Object> list = this.redisTemplate.executePipelined((RedisCallback<?>) connection -> {
    // for (final String s : keys) {
    // connection.get(s.getBytes());
    // }
    // return null;
    // });
    //
    // System.out.println("pipeline获取结果" + list);
    //
    // final List<Object> list1 = this.redisTemplate.opsForValue().multiGet(keys2);
    // System.out.println("multiGet获取结果" + list1);
    // }
}

