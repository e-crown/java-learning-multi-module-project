package com.kv.redis.common.lock;

import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kv.wc.common.utils.DateUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Description: redisson分布式锁, 支持全局与局部锁
 * 
 *
 */
@Slf4j
@Component
public class RedissonLock implements RedisLockTemplate {

    @Autowired
    private RedissonClient redisson;
    /**
     * 锁等待时间
     */
    @Value("${redisson.lock.defaultLockWaitTime:10}")
    private int defaultLockWaitTime;
    /**
     * 锁默认失效时间
     */
    @Value("${redisson.lock.defaultLockLeaseTime:30}")
    private int defaultLockLeaseTime;

    /**
     * 获取锁对象
     * 
     * @param name
     * @return
     */
    @Override
    public RLock getLock(final String name) {
        return this.redisson.getLock(name);
    }

    /**
     * Description: 加锁方法 (使用默认超时时间)
     * 
     * @param supplier
     *            欲加锁的业务方法
     * @param key
     *            锁Key
     */
    @Override
    public <T> T lock(final String key, final Supplier<T> supplier) throws Exception {
        return lock(key, this.defaultLockWaitTime, this.defaultLockLeaseTime, supplier);
    }

    /**
     * Description: 加锁方法
     * 
     * @param supplier
     *            欲加锁的业务方法
     * @param key
     *            锁Key
     * @param lockWaitTime
     *            等待时间
     * @param lockLeaseTime
     *            默认失效时间
     */
    @Override
    public <T> T lock(final String key, long lockWaitTime, long lockLeaseTime, final Supplier<T> supplier)
        throws Exception {
        lockWaitTime = lockWaitTime <= 0 ? this.defaultLockWaitTime : lockWaitTime;
        lockLeaseTime = lockLeaseTime <= 0 ? this.defaultLockLeaseTime : lockLeaseTime;
        log.info("defaultLockWaitTime:{},defaultLockLeaseTime:{}");
        final RLock lock = this.redisson.getLock(key);
        boolean locked = false;
        try {
            locked = lock.tryLock(lockWaitTime, lockLeaseTime, TimeUnit.SECONDS);
            if (!locked) {
                log.info("分布式锁等待超时! 等待时长:{},当前时间为:{}", this.defaultLockWaitTime,
                    DateUtil.getCurrentTime(DateUtil.DEFAULT));
                throw new RuntimeException("获取分布式锁失败");
            }
            return supplier.get();
        } catch (final Exception e) {
            log.error("lock error : 当前时间为:{}", DateUtil.getCurrentTime(DateUtil.DEFAULT));
            throw e;
        } finally {
            if (locked) {
                lock.unlock();
            }
        }
    }

}
