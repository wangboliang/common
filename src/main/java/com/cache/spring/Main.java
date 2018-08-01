package com.cache.spring;

import com.cache.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

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
        ApplicationContext context = new ClassPathXmlApplicationContext("spring-cache-annotation.xml");// 加载 spring 配置文件
        UserService service = (UserService)context.getBean("userService");
        log.info("first query...");
        service.queryUserByIdWithSpringCacheAnnotation(1L);//第一次查询，应该是数据库查询
        log.info("second query...");
        service.queryUserByIdWithSpringCacheAnnotation(1L);//第二次查询，应该直接从缓存返回
    }
}
