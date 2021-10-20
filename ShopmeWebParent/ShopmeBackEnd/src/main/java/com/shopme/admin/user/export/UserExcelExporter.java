package com.shopme.admin.user.export;

import com.shopme.common.entity.User;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserExcelExporter extends AbstractExporter {
    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;

    public UserExcelExporter() {
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
    }

    public void export(List<User> users, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "users", "xlsx", "application/octet-stream");

        String[] headers = {"User ID", "Email", "First Name", "Last Name", "Roles", "Enabled"};
        writeHeaders(headers);
        writeData(users);
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

    private void writeData(List<User> users) {
        int rowIndex = 1;
        for (User user : users) {
            XSSFRow row = sheet.createRow(rowIndex++);
            int columnIndex = 0;
            createCell(row, columnIndex++, user.getId(), null);
            createCell(row, columnIndex++, user.getEmail(), null);
            createCell(row, columnIndex++, user.getFirstName(), null);
            createCell(row, columnIndex++, user.getLastName(), null);
            createCell(row, columnIndex++, user.getRoles(), null);
            createCell(row, columnIndex++, user.isEnabled(), null);
        }
    }
}
