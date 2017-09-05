package com.test;

import com.utils.common.ExcelToSqlConvertor;
import com.utils.common.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;

/**
 * 测试类
 */
@Slf4j
public class AppTest {

    @Test
    public void testExcelToSql() throws Exception {
        String xlsFile = "C:\\Users\\dell\\Desktop\\用户信息.xls";
        String sqlFile = "C:\\Users\\dell\\Desktop\\user.sql";
        // 其中:0  :1  :2  为值所在excel的列号
        String template = "INSERT INTO USER(ID,NAME,AGE) VALUES(':0', ':1', ':2');";
        FileInputStream inputStream = new FileInputStream(new File(xlsFile));
        ExcelToSqlConvertor.convert(inputStream, sqlFile, template, null, null, null);
    }

    @Test
    public void testGenerateUUID() throws Exception {
        Long id = IdWorker.getId();
        log.info("generate id is : {} ", id);
    }

}
