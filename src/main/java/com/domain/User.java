package com.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
@ToString
@AllArgsConstructor
public class User {

    private Long id;
    private String name;
    private String age;

}
