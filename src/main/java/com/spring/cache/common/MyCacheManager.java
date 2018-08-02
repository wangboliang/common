package com.spring.cache.common;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>
 * ConcurrentHashMap缓存管理器
 * </p>
 *
 * @author wangliang
 * @since 2018/8/1
 */
@Slf4j
public class MyCacheManager<T> {
    private Map<String, T> cache = new ConcurrentHashMap<String, T>();

    public T getValue(String key) {
        log.info("get user from concurrentHashMap cache...");
        return cache.get(key);
    }

    public void addOrUpdateCache(String key, T value) {
        log.info("add or update user from concurrentHashMap cache...");
        cache.put(key, value);
    }

    public void evictCache(String key) {
        log.info("evict user from concurrentHashMap cache...");
        if (cache.containsKey(key)) {
            //根据 key 来删除缓存中的一条记录
            cache.remove(key);
        }
    }

    public void evictCache() {
        log.info("evict all user from concurrentHashMap cache...");
        // 清空缓存中的所有记录
        cache.clear();
    }
}
