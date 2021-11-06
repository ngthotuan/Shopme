package com.shopme.admin.export;

import com.shopme.admin.utils.ObjectUtils;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public abstract class ExcelExporter<T> extends AbstractExporter {
    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;

    public ExcelExporter() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
    }

    public void export(List<T> lists, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, getFilePrefix(), "xlsx", "application/octet-stream");

        writeHeaders(getHeaders());
        writeData(lists);
        autoResizeColumn(getHeaders());

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    private void autoResizeColumn(String[] headers) {
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }
    }

    private void writeHeaders(String[] headers) {
        XSSFRow row = sheet.createRow(0);
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        for (int i = 0; i < headers.length; i++) {
            createCell(row, i, headers[i], cellStyle);
        }
    }

    private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle cellStyle) {
        XSSFCell cell = row.createCell(columnIndex);
        cell.setCellStyle(cellStyle);
        if (value instanceof Boolean) {
            cell.setCellValue((boolean) value);
        } else if (value instanceof String) {
            cell.setCellValue((String) value);
        } else if (value instanceof Number) {
            cell.setCellValue((Long) value);
        } else {
            cell.setCellValue(value.toString());
        }
    }

    private void writeData(List<T> lists) {
        int rowIndex = 1;
        for (T obj : lists) {
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;
            for (String fieldName : getFields()) {
                String fieldValue = ObjectUtils.getStringValue(obj, fieldName);
                createCell(row, columnIndex++, fieldValue, null);
            }
        }
    }

    public abstract String[] getHeaders();

    public abstract String[] getFields();

    public abstract String getFilePrefix();

}
