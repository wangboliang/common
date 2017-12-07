package com.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * <p>
 * 用户信息实体类
 * </p>
 *
 * @author wangliang
 * @since 2017/9/5
 */
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
public class User extends Person {

    private Long id;
    private String name;
    private String age;

    public User() {
        super();
    }
}
