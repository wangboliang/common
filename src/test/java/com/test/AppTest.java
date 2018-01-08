package com.test;

import com.domain.User;
import com.utils.serialize.SerializeUtil;
import com.utils.common.IdWorker;
import com.utils.database.ExcelToSql;
import com.utils.database.ListToSql;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.DateUtil;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        List list = new ArrayList();
        User o1 = new User(Long.valueOf(1), "Alan", "18");
        User o2 = new User(Long.valueOf(2), "Bard", "25");
        User o3 = new User(Long.valueOf(3), "Carr", "28");
        list.add(o1);
        list.add(o2);
        list.add(o3);
        // 其中:id  :name  :age 为类属性名称
        String template = "INSERT INTO USER(ID,NAME,AGE) VALUES(':id', ':name', ':age');";
        ListToSql.generateSqlScriptFile(list, template, sqlFile);
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

    /**
     * 测试继承关系的Object序列化和反序列化
     *
     * @throws Exception
     */
    @Test
    public void tesSerializable() throws Exception {
        //Initializes The Object
        User user = new User(111L, "Object序列化", "11");
        user.setAddress("address");
        System.out.println(user);

        String path = "D:\\test.txt";
        //Write Obj to File
        SerializeUtil.write(user, path);

        //Read Obj from File
        Object o = SerializeUtil.read(path);
        System.out.println(o);
    }

    @Test
    public void testMatcher() throws Exception {
//        String template = "INSERT INTO category_property_set VALUES(:id, :createdBy, :createdTime,:updatedBy,:updatedTime,:deleted\" +\n" +
//                "                    \",:propertySetId,:categoryId);";
//        Matcher matcher = Pattern.compile("(:\\w*)").matcher(template);
//        List<String> fieldList = new ArrayList();
//        while (matcher.find()) {
//            fieldList.add(matcher.group().replace(":", ""));
//        }
        String regEx = "(?<=(?<!\\\\)\\$\\{)(.*?)(?=(?<!\\\\)\\})";
        String template = "您的账号于${date}在${device}设备登录，如果不是本人操作，请检查账号密码是否泄漏。";
        Matcher matcher = Pattern.compile(regEx).matcher(template);
        List<String> fieldList = new ArrayList<>();
        while (matcher.find()) {
            fieldList.add(matcher.group());
        }
        String[] contents = new String[]{"2017年12月20日10:20:01", "iphone6"};
        String messageContent = new String(template);
        for (String str : contents) {
            for (String field : fieldList) {
                messageContent = messageContent.replace("${"+field+"}", str);
            }
        }
        log.info("fieldList is: {}" ,fieldList.toString());
        log.info("messageContent is: {}" ,messageContent);
    }

}
