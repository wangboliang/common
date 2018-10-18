package com.domain;

import lombok.*;

import java.io.Serializable;
import java.util.List;

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
@Builder
public class User extends Person {

    private Long id;
    private String name;
    private String age;

    public User() {
        super();
    }

}
