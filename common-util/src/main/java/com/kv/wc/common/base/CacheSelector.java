package com.kv.wc.common.base;

@FunctionalInterface
public interface CacheSelector<T> {
    T select() throws Exception;
}
