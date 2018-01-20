package com.utils.common;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;

/**
 * <p>
 * 获取properties文件内容工具类
 * </p>
 *
 * @author wangliang
 * @since 2018/1/18
 */
@Slf4j
public class PropertiesUtil {

    public static byte[] readFile(String filename) throws IOException, URISyntaxException {
        byte[] buffer = new byte[1024];
        InputStream is = null;

        try {
            is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filename);
            is.read(buffer);
        } finally {
            is.close();
        }

        return buffer;
    }

}
