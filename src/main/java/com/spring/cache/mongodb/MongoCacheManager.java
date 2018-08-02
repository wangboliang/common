package com.spring.cache.mongodb;

import org.springframework.cache.Cache;
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
public class MongoCacheManager extends AbstractCacheManager {

    private Collection<? extends Cache> caches;

    /**
     * Specify the collection of Cache instances to use for this CacheManager.
     */
    public void setCaches(Collection<? extends Cache> caches) {
        this.caches = caches;
    }

    @Override
    protected Collection<? extends Cache> loadCaches() {
        return this.caches;
    }
}
