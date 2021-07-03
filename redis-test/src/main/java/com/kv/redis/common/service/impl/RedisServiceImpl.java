package com.kv.redis.common.service.impl;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.kv.redis.common.domain.RedisInfo;
import com.kv.redis.common.service.RedisService;

/**
 * redis操作实现类
 *
 *
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public List<RedisInfo> getRedisInfo() {
        final List<RedisInfo> infoList = new ArrayList<>();
        final Properties properties = this.redisTemplate.getRequiredConnectionFactory().getConnection().info();
        if (properties == null || properties.size() == 0) {
            return infoList;
        }
        for (final String key : properties.stringPropertyNames()) {
            infoList.add(new RedisInfo(key, properties.getProperty(key)));
        }
        return infoList;
    }

    @Override
    public Map<String, Object> getKeysSize() {
        final Long dbSize = this.redisTemplate.getRequiredConnectionFactory().getConnection().dbSize();
        final Map<String, Object> map = new HashMap<>();
        map.put("create_time", System.currentTimeMillis());
        map.put("dbSize", dbSize);
        return map;
    }

    @Override
    public Map<String, Object> getMemoryInfo() {
        final Properties properties = this.redisTemplate.getRequiredConnectionFactory().getConnection().info("memory");
        if (properties == null || properties.size() == 0) {
            return null;
        }
        Map<String, Object> map = null;
        for (final String key : properties.stringPropertyNames()) {

            if ("used_memory".equals(key)) {
                map = new HashMap<>();
                map.put("used_memory", properties.get(key));
                map.put("create_time", System.currentTimeMillis());
                break;
            }
        }
        return map;
    }

    @Override
    public void set(final String key, final Object value, final long time) {
        this.redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    @Override
    public void set(final String key, final Object value) {
        this.redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public String get(final String key) {
        final Object value = this.redisTemplate.opsForValue().get(key);
        return value == null ? null : value.toString();
    }

    @Override
    public Boolean del(final String key) {
        return this.redisTemplate.delete(key);
    }

    @Override
    public Long del(final List<String> keys) {
        return this.redisTemplate.delete(keys);
    }

    @Override
    public Boolean expire(final String key, final long time) {
        return this.redisTemplate.expire(key, time, TimeUnit.SECONDS);
    }

    @Override
    public Boolean pexpire(final String key, final long time) {
        return this.redisTemplate.expire(key, time, TimeUnit.MILLISECONDS);
    }

    @Override
    public Long getExpire(final String key) {
        return this.redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    @Override
    public Boolean hasKey(final String key) {
        return this.redisTemplate.hasKey(key);
    }

    @Override
    public Long incr(final String key, final long delta) {
        return this.redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Long decr(final String key, final long delta) {
        return this.redisTemplate.opsForValue().increment(key, -delta);
    }

    @Override
    public Object hGet(final String key, final String hashKey) {
        return this.redisTemplate.opsForHash().get(key, hashKey);
    }

    @Override
    public Boolean hSet(final String key, final String hashKey, final Object value, final long time) {
        this.redisTemplate.opsForHash().put(key, hashKey, value);
        return expire(key, time);
    }

    @Override
    public void hSet(final String key, final String hashKey, final Object value) {
        this.redisTemplate.opsForHash().put(key, hashKey, value);
    }

    @Override
    public Map<Object, Object> hGetAll(final String key) {
        return this.redisTemplate.opsForHash().entries(key);
    }

    @Override
    public Boolean hSetAll(final String key, final Map<String, Object> map, final long time) {
        this.redisTemplate.opsForHash().putAll(key, map);
        return expire(key, time);
    }

    @Override
    public void hSetAll(final String key, final Map<String, ?> map) {
        this.redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public void hDel(final String key, final Object... hashKey) {
        this.redisTemplate.opsForHash().delete(key, hashKey);
    }

    @Override
    public Boolean hHasKey(final String key, final String hashKey) {
        return this.redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    @Override
    public Long hIncr(final String key, final String hashKey, final Long delta) {
        return this.redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    @Override
    public Long hDecr(final String key, final String hashKey, final Long delta) {
        return this.redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    @Override
    public Set<Object> sMembers(final String key) {
        return this.redisTemplate.opsForSet().members(key);
    }

    @Override
    public Long sAdd(final String key, final Object... values) {
        return this.redisTemplate.opsForSet().add(key, values);
    }

    @Override
    public Long sAdd(final String key, final long time, final Object... values) {
        final Long count = this.redisTemplate.opsForSet().add(key, values);
        expire(key, time);
        return count;
    }

    @Override
    public Boolean sIsMember(final String key, final Object value) {
        return this.redisTemplate.opsForSet().isMember(key, value);
    }

    @Override
    public Long sSize(final String key) {
        return this.redisTemplate.opsForSet().size(key);
    }

    @Override
    public Boolean zadd(final String key, final String member, final Double score) {
        return this.redisTemplate.opsForZSet().add(key, member, score);
    }

    @Override
    public Long sRemove(final String key, final Object... values) {
        return this.redisTemplate.opsForSet().remove(key, values);
    }

    @Override
    public List<Object> lRange(final String key, final long start, final long end) {
        return this.redisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public Long lSize(final String key) {
        return this.redisTemplate.opsForList().size(key);
    }

    @Override
    public Object lIndex(final String key, final long index) {
        return this.redisTemplate.opsForList().index(key, index);
    }

    @Override
    public Long lPush(final String key, final Object value) {
        return this.redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public Long lPush(final String key, final Object value, final long time) {
        final Long index = this.redisTemplate.opsForList().rightPush(key, value);
        expire(key, time);
        return index;
    }

    @Override
    public Long lPushAll(final String key, final Object... values) {
        return this.redisTemplate.opsForList().rightPushAll(key, values);
    }

    @Override
    public Long lPushAll(final String key, final Long time, final Object... values) {
        final Long count = this.redisTemplate.opsForList().rightPushAll(key, values);
        expire(key, time);
        return count;
    }

    @Override
    public Long lRemove(final String key, final long count, final Object value) {
        return this.redisTemplate.opsForList().remove(key, count, value);
    }

    @Override
    public Set<Object> zrangeByScore(final String key, final Double min, final Double max) {
        return this.redisTemplate.opsForZSet().rangeByScore(key, min, max);
    }

    @Override
    public Long zremrangeByScore(final String key, final String start, final String end) {
        return null;
    }

    @Override
    public Long zrem(final String key, final String members) {
        if (key == null || members == null) {
            return null;
        }
        return this.redisTemplate.opsForZSet().remove(key, members);
    }

    /**
     * 1 if the key exists. 0 if the key does not exist.
     * 
     * @param key
     * @return
     */
    @Override
    public Boolean exists(final String... key) {
        final Long existLong = this.redisTemplate.countExistingKeys(CollectionUtils.arrayToList(key));
        if (existLong == null || existLong.intValue() == 0) {
            return false;
        }
        return true;
    }
}
