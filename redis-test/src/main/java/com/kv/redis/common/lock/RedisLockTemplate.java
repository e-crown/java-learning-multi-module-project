package com.kv.redis.common.lock;

import java.util.function.Supplier;

import org.redisson.api.RLock;

/**
 *
 */
public interface RedisLockTemplate {
    /**
     * 获取锁对象
     * 
     * @param name
     * @return
     */
    RLock getLock(String name);

    /**
     * 使用分布式锁，使用锁默认超时时间。
     *
     * @param callback
     * @return
     */
    <T> T lock(String key, Supplier<T> callback) throws Exception;

    /**
     * 使用分布式锁。自定义锁的超时时间
     *
     * @param callback
     * @param leaseTime
     *            锁超时时间。超时后自动释放锁。
     * @param lockLeaseTime
     * @return
     */
    <T> T lock(String key, long leaseTime, long lockLeaseTime, Supplier<T> callback) throws Exception;
}
