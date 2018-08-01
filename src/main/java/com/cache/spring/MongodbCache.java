package com.cache.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.concurrent.Callable;

/**
 * <p>
 * 自定义缓存实现
 * </p>
 *
 * @author wangliang
 * @since 2018/8/1
 */
@Slf4j
public class MongodbCache implements Cache {

    MongoTemplate mongoTemplate;
    private String name;

    public MongodbCache(String name, MongoTemplate mongoTemplate) {
        this.name = name;
        this.mongoTemplate = mongoTemplate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getNativeCache() {
        return null;
    }

    public ValueWrapper get(Object o) {
        log.info("get user from mongodb...");
        return null;
    }

    public <T> T get(Object o, Class<T> aClass) {
        return null;
    }

    public <T> T get(Object o, Callable<T> callable) {
        return null;
    }

    public void put(Object o, Object o1) {
        log.info("put user to mongodb...");
    }

    public ValueWrapper putIfAbsent(Object o, Object o1) {
        return null;
    }

    public void evict(Object o) {
        log.info("evict user to mongodb...");
    }

    public void clear() {

    }
}
