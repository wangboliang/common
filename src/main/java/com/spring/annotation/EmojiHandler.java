package com.spring.annotation;

import com.utils.common.EmojiUtil;

import java.lang.reflect.Field;

/**
 * <p>
 * emoji parse handler
 * </p>
 *
 * @author wangliang
 * @since 2019/11/27
 */
public class EmojiHandler {

    public static Object parseToHtmlHexadecimal(Object arg) throws Exception {
        Field[] fields = arg.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(EmojiParse.class)) {
                EmojiParse annotation = field.getAnnotation(EmojiParse.class);
                if (annotation.encode() == true) {
                    // 设置private类型允许访问
                    field.setAccessible(Boolean.TRUE);
                    String value = field.get(arg) == null ? "" : field.get(arg).toString();
                    field.set(arg, EmojiUtil.parseToHtmlHexadecimal(value));
                    field.setAccessible(Boolean.FALSE);
                }
            }
        }
        return null;
    }

    public static Object parseToUnicode(Object arg) throws Exception {
        Field[] fields = arg.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(EmojiParse.class)) {
                EmojiParse annotation = field.getAnnotation(EmojiParse.class);
                if (annotation.decode() == true) {
                    // 设置private类型允许访问
                    field.setAccessible(Boolean.TRUE);
                    String value = field.get(arg) == null ? "" : field.get(arg).toString();
                    field.set(arg, EmojiUtil.parseToUnicode(value));
                    field.setAccessible(Boolean.FALSE);
                }
            }
        }
        return null;
    }
}
