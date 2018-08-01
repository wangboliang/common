package com.cache;

import com.cache.common.MyCacheManager;
import com.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;

/**
 * <p>
 * description
 * </p>
 *
 * @author wangliang
 * @since 2018/8/1
 */
@Slf4j
public class UserService {

    //ConcurrentHashMap缓存管理器
    private MyCacheManager<User> cacheManager;

    public UserService() {
        this.cacheManager = new MyCacheManager<User>();
    }

    /**
     * 不用任何第三方的组件来实现
     *
     * @param id
     * @return
     */
    public User queryUserById(Long id) {
        //首先查询缓存
        User user = cacheManager.getValue(id.toString());
        if (user != null) {
            return user;
        }

        user = queryUserByIdFromDb(id);
        if (user != null) {
            //将数据库查询的结果更新到缓存中
            cacheManager.addOrUpdateCache(id.toString(), user);
        }
        return user;
    }

    /**
     * 使用spring cache注解实现
     *
     * @param id
     * @return
     */
//    @Cacheable(value = "userCache", key = "#id")
    @Cacheable(value = "mongodbCache", key = "#id")
    public User queryUserByIdWithSpringCacheAnnotation(Long id) {
        return queryUserByIdFromDb(id);
    }

    /**
     * 从数据库中查询
     *
     * @param id
     * @return
     */
    private User queryUserByIdFromDb(Long id) {
        log.info("query user from db...");
        User user = new User(1L, "bright", "18");
        return user;
    }

    public void reload() {
        cacheManager.evictCache();
    }
}
