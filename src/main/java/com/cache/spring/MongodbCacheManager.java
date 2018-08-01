package com.cache.spring;

import org.springframework.cache.support.AbstractCacheManager;

import java.util.Collection;

/**
 * <p>
 * mongodb缓存管理器
 * </p>
 *
 * @author wangliang
 * @since 2018/8/1
 */
public class MongodbCacheManager extends AbstractCacheManager {

    private Collection<? extends MongodbCache> caches;

    /**
     * Specify the collection of Cache instances to use for this CacheManager.
     */
    public void setCaches(Collection<? extends MongodbCache> caches) {
        this.caches = caches;
    }

    @Override
    protected Collection<? extends MongodbCache> loadCaches() {
        return this.caches;
    }
}
