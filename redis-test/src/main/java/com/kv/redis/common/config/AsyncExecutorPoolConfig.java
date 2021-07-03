package com.kv.redis.common.config;

import java.util.concurrent.ThreadPoolExecutor;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 *
 */
@Configuration
public class AsyncExecutorPoolConfig extends AsyncConfigurerSupport {

    @Override
    public AsyncTaskExecutor getAsyncExecutor() {

        final ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 此方法返回可用处理器的虚拟机的最大数量; 不小于1
        final int core = Runtime.getRuntime().availableProcessors();
        executor.setCorePoolSize(core * 2);
        executor.setMaxPoolSize(core * 2);
        executor.setQueueCapacity(10000);
        // 如果传入值大于0，底层队列使用的是LinkedBlockingQueue,否则默认使用SynchronousQueue
        executor.setKeepAliveSeconds(10);
        executor.setThreadNamePrefix("AsyncTaskExecutor-");
        // 设置拒绝策略
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();
        return executor;
    }
}