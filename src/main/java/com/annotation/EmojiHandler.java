package com.annotation;

import com.utils.common.EmojiUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * <p>
 * emoji parse handler
 * </p>
 *
 * @author wangliang
 * @since 2019/11/27
 */
@Slf4j
@Aspect
@Component
public class EmojiHandler {

    @Pointcut("@annotation(com.annotation.EmojiParse)")
    public void desPoint() {
    }

    public Object parse(ProceedingJoinPoint point) throws Throwable {
        Object[] methodArgs = point.getArgs();
        for (Object arg : methodArgs) {
            encode(arg);
        }
        Object result = point.proceed();
        return EmojiUtil.parseToUnicode(result.toString());
    }

    public Object encode(Object arg) throws Throwable {
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

    public Object decode(Object arg) throws Throwable {
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
