package com.utils.database;

import com.sun.xml.internal.ws.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * 生成sql脚本
 * </p>
 *
 * @author wangliang
 * @since 2017/9/5
 */
@Slf4j
public class ListToSql {

    /**
     * 生成sql脚本文件
     *
     * @param list     数据源
     * @param template sql语句模版
     * @param sqlFile  要输出的sql脚本文件路径
     * @return
     */
    public static boolean generateSqlScriptFile(List list, String template, String sqlFile) {
        boolean result = false;
        try {

            //生成sql脚本
            List<String> sqlList = generateSqlStatement(list, template);

            //创建sql脚本文件
            File file = createFile(sqlFile);

            Path filePath = Paths.get(file.getPath());
            //写入数据到文件中
            Files.write(filePath, sqlList, Charset.forName("UTF-8"));
        } catch (Exception e) {
            log.error("生成sql脚本异常", e);
        }

        return result;
    }

    /**
     * 生成insert sql语句
     *
     * @param list     数据源
     * @param template sql语句模版
     * @return
     */
    public static List<String> generateSqlStatement(List list, String template) throws Exception {
        // 获取sql中的所有字段点位符
        Matcher matcher = Pattern.compile("(:\\w*)").matcher(template);
        List<String> fields = new ArrayList();
        while (matcher.find()) {
            fields.add(matcher.group().replace(":", ""));
        }

        List<String> sqlList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            for (Object o : list) {
                Class clazz = o.getClass();
                // 组装sql语句
                String sql = new String(template);
                for (String field : fields) {
                    String getterMethodName = "get" + StringUtils.capitalize(field);
                    Method method = clazz.getMethod(getterMethodName);
                    sql = sql.replace(":" + field, String.valueOf(method.invoke(o)));
                }
                log.info(sql);
                sqlList.add(sql);
            }
        }
        return sqlList;
    }

    /**
     * 创建文件
     *
     * @param pathName 文件路径
     */
    public static File createFile(String pathName) {
        File file = null;
        try {
            file = new File(pathName);
            //if file does not exists, then create it
//            if (!file.exists()) {
                file.createNewFile();
//            }
        } catch (IOException e) {
            log.error("文件创建失败", e);
        }
        return file;
    }
}
