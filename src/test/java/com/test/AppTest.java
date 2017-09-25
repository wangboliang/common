package com.test;

import com.domain.User;
import com.utils.common.IdWorker;
import com.utils.database.ExcelToSql;
import com.utils.database.ListToSql;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

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
        ExcelToSql.convert(inputStream, sqlFile, template, null, null, null);
    }

    @Test
    public void testListToSql() throws Exception {
        String sqlFile = "C:\\Users\\dell\\Desktop\\user.sql";
        List list = new ArrayList<>();
        User o1 = new User(Long.valueOf(1), "Alan", "18");
        User o2 = new User(Long.valueOf(2), "Bard", "25");
        User o3 = new User(Long.valueOf(3), "Carr", "28");
        list.add(o1);
        list.add(o2);
        list.add(o3);
        // 其中:id  :name  :age 为类属性名称
        String template = "INSERT INTO USER(ID,NAME,AGE) VALUES(':id', ':name', ':age');";
        ListToSql.generateSqlScripFile(list, template, sqlFile);
    }

    @Test
    public void testGenerateUUID() throws Exception {
        Long id = IdWorker.getId();
        log.info("generate id is : {} ", id);
    }


    @Test
    public void tesInteger() throws Exception {
        //JDK.1.8
        Integer a = 127, b = 127;
        System.out.print(a == b);

        Integer c = 128, d = 128;
        System.out.print(c == d);
    }

}
