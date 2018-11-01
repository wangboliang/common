package com.utils.common;

import com.domain.User;
import com.utils.database.ListToSql;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * excel工具类
 * </p>
 *
 * @author wangliang
 * @since 2018/11/1
 */
@Slf4j
public class ExcelUtil {

    public static void main(String args[]) throws Exception {
        exportBigDataExcel(1000000);
    }

    public static void exportBigDataExcel(int size) throws Exception {
        Sheet sheet = null; // 工作表对象
        Row nRow = null;
        Cell nCell = null;
        try {
            int pageSize = 100000;
            SXSSFWorkbook wb = new SXSSFWorkbook(100000);
            // 获取数据库中行数
            Integer dataCount = selectDataCount(size);
            // 根据函数，获取提取次数
            int exportTimes = dataCount % pageSize > 0 ? dataCount / pageSize + 1 : dataCount / pageSize;
            // 按次数将数据写入文件
            for (int i = 0; i < exportTimes; i++) {
                sheet = wb.createSheet("sheet" + i);
                sheet = wb.getSheetAt(i);
                // 第一行
                nRow = sheet.createRow(0);
                nCell = nRow.createCell(0);
                nCell.setCellValue("ID");
                nCell = nRow.createCell(1);
                nCell.setCellValue("NAME");
                nCell = nRow.createCell(2);
                nCell.setCellValue("AGE");
                int startRow = i * pageSize;
                List<User> list = pageData(size, startRow, pageSize);
                for (int j = 0; j < list.size(); j++) {
                    User user = list.get(j);
                    Row dataRow = sheet.createRow(j + 1);
                    nCell = dataRow.createCell(0);
                    nCell.setCellValue(user.getId());
                    nCell = dataRow.createCell(1);
                    nCell.setCellValue(user.getAge());
                    nCell = dataRow.createCell(2);
                    nCell.setCellValue(user.getName());
                }
            }
            String fileName = "user.xlsx";
            outputExcel(wb, fileName);
        } catch (Exception e) {
            log.error("export excel exception: {}", e);
        }
    }

    public static void outputExcel(Workbook workbook, String fileName) throws Exception {
        //将excel写入流中
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            workbook.write(byteArrayOutputStream);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            ListToSql.write(inputStream, fileName);
        } catch (Exception e) {
            log.error("export excel exception: {}", e);
        } finally {
            if (null != byteArrayOutputStream) {
                byteArrayOutputStream.close();
            }
        }
    }

    static int selectDataCount(int size) {
        return size;
    }

    static List<User> pageData(int size, int startRow, int pageSize) {
        List<User> userList = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            User user = new User(Long.valueOf(i), "age is:" + i, "name is:" + i);
            userList.add(user);
        }
        int fromIndex = startRow;
        int toIndex = startRow + pageSize;
        return userList.subList(fromIndex, toIndex);
    }
}
