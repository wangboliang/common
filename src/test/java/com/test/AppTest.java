package com.test;

import com.domain.EmojiBean;
import com.domain.Person;
import com.domain.User;
import com.spring.annotation.EmojiHandler;
import com.utils.common.ExcelUtil;
import com.utils.common.IdWorker;
import com.utils.common.ListUtil;
import com.utils.database.ExcelToSql;
import com.utils.database.ListToSql;
import com.utils.serialize.SerializeUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 测试类
 */
@Slf4j
public class AppTest {

    /**
     * 测试excel转sql脚本
     *
     * @throws Exception
     */
    @Test
    public void testExcelToSql() throws Exception {
        String xlsFile = "C:\\Users\\dell\\Desktop\\用户信息.xls";
        String sqlFile = "C:\\Users\\dell\\Desktop\\user.sql";
        // 其中:0  :1  :2  为值所在excel的列号
        String template = "INSERT INTO USER(ID,NAME,AGE) VALUES(':0', ':1', ':2');";
        FileInputStream inputStream = new FileInputStream(new File(xlsFile));
        ExcelToSql.convert(inputStream, sqlFile, template, null, null, null);
    }

    /**
     * 测试list转sql脚本
     *
     * @throws Exception
     */
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

    /**
     * 测试序列工具
     *
     * @throws Exception
     */
    @Test
    public void testGenerateUUID() throws Exception {
        Long id = IdWorker.getId();
        log.info("generate id is : {} ", id);
    }

    /**
     * 测试Integer比较
     * 两个Integer引用类型==号比较，JDK1.5之后-128-127之间自动拆箱
     *
     * @throws Exception
     */
    @Test
    public void tesInteger() throws Exception {
        //JDK.1.8
        Integer a = 127, b = 127;
        Integer c = 128, d = 128;
        log.info("Integer 127 == Integer 127 : {}", a == b);//true
        log.info("Integer 128 == Integer 128 : {}", c == d);//false
        log.info("Integer 127 == int 127 : {}", a == 127);//true
        log.info("Integer 127 equals int 127 : {}", a.equals(127));//true
        log.info("Integer 128 == int 128 : {}", c == 128);//true
        log.info("Integer 128 equals int 128 : {}", c.equals(128));//true
    }

    /**
     * 测试继承关系的Object序列化和反序列化
     *
     * @throws Exception
     */
    @Test
    public void tesSerializable() throws Exception {
        //Initializes The Object
        User user = User.builder().id(111L).name("Object序列化").age("11").build();
        user.setAddress("address");
        System.out.println(user);

        String path = "D:\\test.txt";
        //Write Obj to File
        SerializeUtil.write(user, path);

        //Read Obj from File
        Object o = SerializeUtil.read(path);
        System.out.println(o);
    }

    /**
     * 测试正则表达式API
     *
     * @throws Exception
     */
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
                messageContent = messageContent.replace("${" + field + "}", str);
            }
        }
        log.info("fieldList is: {}", fieldList.toString());
        log.info("messageContent is: {}", messageContent);
    }

    /**
     * 测试list处理
     *
     * @throws Exception
     */
    @Test
    public void testListUtil() throws Exception {
        List<String> firstList = new ArrayList<>();
        firstList.add("1");
        firstList.add("2");
        firstList.add("3");
        firstList.add("4");
        List<String> secondList = new ArrayList<>();
        secondList.add("2");
        secondList.add("3");
        secondList.add("5");
        secondList.add("6");
        log.info("差集 : {}", ListUtil.subtract(firstList, secondList));
        log.info("差集 : {}", ListUtil.subtract(secondList, firstList));
        log.info("交集 : {}", ListUtil.intersection(firstList, secondList));
        log.info("并集 : {}", ListUtil.union(firstList, secondList));
    }

    /**
     * 大数据量excel导出
     *
     * @throws Exception
     */
    @Test
    public void exportBigDataExcel() throws Exception {
        long beginTime = System.currentTimeMillis();
        int size = 1000000;
        ExcelUtil.exportBigDataExcel(1000000);
        long endTime = System.currentTimeMillis();
        log.info("{}条数据excel导出用时: {} 毫秒", size, endTime - beginTime);
    }

    /**
     * emoji注解测试
     *
     * @throws Exception
     */
    @Test
    public void emojiParse() throws Throwable {
        EmojiBean bean = new EmojiBean("\uD83D\uDE04", "\uD83D\uDE04");
        log.info("before emojiParse: {}", bean);
        EmojiHandler.parseToHtmlHexadecimal(bean);
        log.info("after emojiParse encode: {}", bean);
        EmojiHandler.parseToUnicode(bean);
        log.info("after emojiParse decode: {}", bean);
    }

    /**
     * test
     *
     * @throws Exception
     */
    @Test
    public void test() throws Throwable {
        Comparator<User> comparator = (p1, p2) -> p1.getName().compareTo(p2.getName());
        User p1 = new User(1L,"John", "12");
        User p2 = new User(2L,"Alice", "15");
        comparator.compare(p1, p2);             // > 0
        comparator.reversed().compare(p1, p2);  // < 0

        Consumer<User> greeter = (p) -> System.out.println("Hello, " + p.getName());
        greeter.accept(p1);
    }
}
