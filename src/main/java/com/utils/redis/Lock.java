package com.utils.redis;

/**
 * <p>
 *
 * </p>
 *
 * @author wangliang
 * @since 2020/5/27
 */
public interface Lock {

    /**
     * 创建锁
     * @return
     */
    boolean getLock(String key, String value);

    /**
     * 解锁
     * @return
     */
    boolean unLock(String key, String value);
}