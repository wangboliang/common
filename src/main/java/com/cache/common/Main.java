package com.cache.common;

import com.cache.UserService;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * main测试
 * </p>
 *
 * @author wangliang
 * @since 2018/8/1
 */
@Slf4j
public class Main {

    public static void main(String[] args) {
        UserService service = new UserService();
        log.info("first query...");
        service.queryUserById(1L);//第一次查询，应该是数据库查询
        log.info("second query...");
        service.queryUserById(1L);//第二次查询，应该直接从缓存返回
    }
}
