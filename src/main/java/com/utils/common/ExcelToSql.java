package com.utils.common;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * <p>
 * excel数据生成sql脚本工具类
 * </p>
 *
 * @author wangliang
 * @since 2017/8/28
 */
@Slf4j
public class ExcelToSql {

    /**
     * excel数据生成sql脚本
     *
     * @param inputStream     excel文件输入流
     * @param outDir          sql脚本输出路径
     * @param tableName       表名gi
     * @param fieldList        字段集合（按cell顺序设置值）
     * @param hasDefaultValue 是否设置默认值
     * @return
     */
    public static boolean excelToSql(InputStream inputStream, String outDir, String tableName, LinkedList<String> fieldList, boolean hasDefaultValue) {
        boolean result = false;
        try {
            List<Map<String, String>> mapList = new ArrayList<>();

            //解析excel
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0);
            //排除表头
            for (int rowNum = sheet.getFirstRowNum() + 1; rowNum < sheet.getLastRowNum(); rowNum++) {
                Row row = sheet.getRow(rowNum);
                if (row == null) {
                    continue;
                }
                Map<String, String> map = new TreeMap<>();
                for (int cellNum = row.getFirstCellNum(); cellNum < row.getLastCellNum(); cellNum++) {
                    Cell cell = row.getCell(cellNum);
                    if (cell == null) {
                        continue;
                    }
                    for (int i = 0; i < fieldList.size(); i++) {
                        if (cellNum == i) {
                            String field = fieldList.get(i);
                            map.put(field, cell.getStringCellValue());
                        }
                    }
                }
                mapList.add(map);
            }

            //生成sql脚本
            List<String> sqlList = generateSql(mapList, tableName, hasDefaultValue);

            String pathName = outDir + tableName + ".sql";
            //创建sql脚本文件
            File file = createFile(pathName);

            Path filePath = Paths.get(file.getPath());
            //写入数据到文件中
            Files.write(filePath, sqlList, Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.error("excel文件解析异常", e);
        } catch (InvalidFormatException e) {
            log.error("excel文件解析异常", e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException io) {
                    log.error("文件流关闭异常", io);
                }
            }
        }

        return result;
    }

    /**
     * 生成insert sql语句
     *
     * @param mapList         字段属性名称与属性值
     * @param tableName       表名
     * @param hasDefaultValue 是否设置默认值
     * @return
     */
    public static List<String> generateSql(List<Map<String, String>> mapList, String tableName, boolean hasDefaultValue) {
        List<String> sqlList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(sqlList)) {
            Long nowTime = System.currentTimeMillis();
            for (Map<String, String> map : mapList) {
                StringBuilder fields = new StringBuilder();
                StringBuilder values = new StringBuilder();
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    fields.append(entry.getKey()).append(",");
                    values.append(entry.getValue()).append(",");
                }
                if (hasDefaultValue) {
                    fields.append("created_by,created_time,updated_by,updated_time,deleted,");
                    values.append(nowTime).append(",").append(nowTime).append(nowTime).append(",").append(nowTime).append(",").append(1).append(",");
                }
                StringBuilder insertSql = new StringBuilder();
                insertSql.append("INSERT INTO ").append(tableName).append("(").append(fields.substring(0, fields.length() - 1)).append(")")
                        .append("VALUES").append("(").append(values.substring(0, values.length() - 1)).append(")");
                sqlList.add(insertSql.toString());
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
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e) {
            log.error("文件创建失败", e);
        }
        return file;
    }

}
