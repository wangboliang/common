package com.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * <p>
 * 这是调用处理器接口，它自定义了一个 invoke 方法，用于集中处理在动态代理类对象上的方法调用
 * 通常在该方法中实现对委托类的代理访问
 * </p>
 *
 * @author wangliang
 * @since 2018/3/6
 */
public class UserInvocationHandler implements InvocationHandler {

    private IUserService userService;

    public UserInvocationHandler(IUserService userService) {
        this.userService = userService;
    }

    /**
     * 该方法负责集中处理动态代理类上的所有方法调用。调用处理器根据这三个参数进行预处理或分派到委托类实例上发射执行
     *
     * @param proxy  代理类实例
     * @param method 被调用的方法对象
     * @param args   调用参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.print("开启事务...");
        method.invoke(userService, args);
        System.out.print("...提交事务!");
        return null;
    }
}
