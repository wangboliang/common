package com.test;

import com.utils.common.ExcelToSql;
import com.utils.common.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.TreeSet;

/**
 * 测试类
 */
@Slf4j
public class AppTest {

    @Test
    public void testExcelToSql() throws Exception {
        String filePath = "";
        String outDir = "";
        String tableName = "user";
        LinkedList fieldList = new LinkedList();
        fieldList.add("id");
        fieldList.add("username");
        fieldList.add("password");
        File file = new File(filePath);
        InputStream inputStream = new FileInputStream(file);
        ExcelToSql.excelToSql(inputStream, outDir, tableName, fieldList,true);
    }

    @Test
    public void testGenerateUUID() throws Exception {
        Long id = IdWorker.getId();
        log.info("generate id is : {} ", id);
    }

}
