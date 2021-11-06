package com.shopme.admin.category.export;

import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.Category;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryExcelExporter extends AbstractExporter {
    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;

    public CategoryExcelExporter() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
    }

    public void export(List<Category> categories, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "categories", "xlsx", "application/octet-stream");

        String[] headers = {"Category ID", "Category Name", "Enabled"};
        writeHeaders(headers);
        writeData(categories);
        autoResizeColumn(headers);

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

    private void writeData(List<Category> categories) {
        int rowIndex = 1;
        for (Category category : categories) {
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;
            createCell(row, columnIndex++, category.getId(), null);
            createCell(row, columnIndex++, category.getName().replace("--", "  "), null);
            createCell(row, columnIndex++, category.isEnabled(), null);
        }
    }
}
