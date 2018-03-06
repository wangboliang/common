package com.proxy;

/**
 * <p>
 *
 * </p>
 *
 * @author wangliang
 * @since 2018/3/6
 */
public class UserService implements IUserService {
    @Override
    public void save() {
        System.out.print("保存数据");
    }
}
