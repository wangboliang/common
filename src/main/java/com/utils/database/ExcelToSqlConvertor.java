package com.utils.database;

/**
 * <p>
 * xls转sql工具
 * </p>
 *
 * @author wangliang
 * @since 2017/9/5
 */

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * xls转sql工具
 * </p>
 *
 * @author wangliang
 * @since 2017/9/5
 */
@Slf4j
public class ExcelToSqlConvertor {

    /**
     * 将xls文件按照指定的sql语句模板转换成sql脚本。
     *
     * @param inputStream xls文件流
     * @param sqlFile     要输出的sql脚本文件路径
     * @param template    sql语句模板
     * @param sheetIndex  xls中标签页的序号(从0开始)
     * @param start       转换开始的行数 (从0开始)
     * @param end         转换结束的行数 (从0开始)
     * @throws IOException
     */
    public static void convert(InputStream inputStream, String sqlFile, String template, Integer sheetIndex, Integer start, Integer end)
            throws IOException, InvalidFormatException {
        // 获取工作薄
        Workbook workbook = WorkbookFactory.create(inputStream);
        if (null == sheetIndex || sheetIndex < 0) {
            sheetIndex = 0;
        }
        Sheet sheet = workbook.getSheetAt(sheetIndex);

        // 获取sql中的所有字段点位符
        Matcher matcher = Pattern.compile("(:\\d+)").matcher(template);
        List<Integer> columns = new ArrayList();
        while (matcher.find()) {
            columns.add(Integer.valueOf(matcher.group().replace(":", "")));
        }

        // 输出sql脚本文件
        PrintWriter writer = new PrintWriter(new FileWriter(sqlFile));
        log.info("Writing sql statements to file: {} ", sqlFile);
        log.info("-------------------------------------------------------------------------------");
        if (null == start || start < 0) {
            start = sheet.getFirstRowNum() + 1;//排除表头
        }
        if (null == end || end < 0) {
            end = sheet.getLastRowNum() + 1;
        }
        for (int rowNum = start; rowNum < end; rowNum++) {
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                continue;
            }

            // 组装sql语句
            String line = new String(template);
            for (Integer column : columns) {
                line = line.replace(":" + column, parseCellValue(row.getCell(column)));
            }
            log.info(line);
            writer.println(line);
        }
        writer.flush();
        writer.close();
        log.info("-------------------------------------------------------------------------------");
    }

    /**
     * 解析单元格的数据值
     *
     * @param cell 单元格
     */
    private static String parseCellValue(Cell cell) {
        String cellValue;
        DecimalFormat df = new DecimalFormat("#");
        switch (cell.getCellType()) {
            case Cell.CELL_TYPE_STRING:
                cellValue = cell.getRichStringCellValue().getString().trim();
                break;
            case Cell.CELL_TYPE_NUMERIC:
                cellValue = df.format(cell.getNumericCellValue()).toString();
                break;
            case Cell.CELL_TYPE_BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue()).trim();
                break;
            case Cell.CELL_TYPE_FORMULA:
                cellValue = cell.getCellFormula();
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }

}
