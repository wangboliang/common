package com.utils.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * <p>
 * Redis分布式锁
 * </p>
 *
 * @author wangliang
 * @since 2020/5/27
 */
@Component
public class RedisLock implements Lock {

    private static final Logger logger = LoggerFactory.getLogger(RedisLock.class);
    private static final int EXPIRE = 60 * 60;

    @Autowired
    JedisPool jedisPool;

    @Override
    public boolean getLock(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis == null) {
                return false;
            }
            return jedis.set(key, value, "NX", "PX", EXPIRE).equalsIgnoreCase("ok");
        } catch (Exception e) {
            logger.error("【获取Redis分布式锁异常】: {}", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }

    @Override
    public boolean unLock(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            if (jedis == null) {
                return false;
            }
            //lua脚本原子操作判断是否是自己的锁：
            // if redis.call('get',#{key})==#{value} then return redis.cal('del',#{key}) else return 0 end
            String script = String.format("if redis.call('get','%s')=='%s' then return redis.call('del','%s') else return 0 end", key, value, key);
            return Integer.valueOf(jedis.eval(script).toString()) == 1;
        } catch (Exception e) {
            logger.error("【释放Redis分布式锁异常】: {}", e);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }
}
