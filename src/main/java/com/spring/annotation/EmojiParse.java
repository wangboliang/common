package com.spring.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * emoji parse annotation
 * </p>
 *
 * @author wangliang
 * @since 2019/11/27
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface EmojiParse {
    /**
     * decode. default false
     */
    public boolean decode() default false;
    /**
     * encode. default false
     */
    public boolean encode() default false;
}
