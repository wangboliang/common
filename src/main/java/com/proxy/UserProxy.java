package com.proxy;

import java.lang.reflect.Proxy;

/**
 * <p>
 * 这是 Java 动态代理机制的主类，它提供了一组静态方法来为一组接口动态地生成代理类及其对象。
 * <p>
 * 1.通过实现 InvocationHandler 接口创建自己的调用处理器；
 * 2.通过为 Proxy 类指定 ClassLoader 对象和一组 interface 来创建动态代理类；
 * 3.通过反射机制获得动态代理类的构造函数，其唯一参数类型是调用处理器接口类型；
 * 4.通过构造函数创建动态代理类实例，构造时调用处理器对象作为参数被传入。
 * </p>
 *
 * @author wangliang
 * @since 2018/3/6
 */
public class UserProxy implements IUserService {

    public static void main(String[] args) {
        UserProxy proxy = new UserProxy();
        proxy.save();
    }

    @Override
    public void save() {
        //创建目标类实例
        IUserService targetInstance = new UserService();
        //创建调用处理器
        UserInvocationHandler handler = new UserInvocationHandler(targetInstance);
        //创建动态代理类的实例
        IUserService proxyInstance = (IUserService) Proxy.newProxyInstance(targetInstance.getClass().getClassLoader(),
                targetInstance.getClass().getInterfaces(), handler);
        //调用代理类的方法
        proxyInstance.save();
    }
}
