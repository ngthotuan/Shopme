package com.shopme.admin.utils;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.*;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public abstract class Exporter<T> {

    private final HttpServletResponse response;
    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;

    public Exporter(HttpServletResponse response) {
        this.response = response;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet();
    }

    private void setResponseHeader(String prefix, String extension, String contentType) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
        String currentDate = dateFormat.format(new Date());
        String fileName = String.format("%s_%s.%s", prefix, currentDate, extension);

        response.setContentType(contentType + "; charset=utf-8");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
    }

    public abstract String getPDFTitle();

    public abstract float[] getTableWidths();

    public abstract String[] getHeaders();

    public abstract String[] getFields();

    public abstract String getFilePrefix();

    // CSV
    public void exportCSV(List<T> lists) throws IOException {
        setResponseHeader(getFilePrefix(), "csv", "text/csv");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.EXCEL_PREFERENCE);
        csvBeanWriter.writeHeader(getHeaders());
        for (T obj : lists) {
            csvBeanWriter.write(obj, getFields());
        }
        csvBeanWriter.close();
    }

    // END CSV

    // Excel
    public void exportExcel(List<T> lists) throws IOException {
        setResponseHeader(getFilePrefix(), "xlsx", "application/octet-stream");

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
    // END Excel

    // PDF
    public void exportPDF(List<T> lists) throws IOException {
        setResponseHeader(getFilePrefix(), "pdf", "application/pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = new Font();
        font.setFamily(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLUE);
        font.setSize(20f);
        Paragraph paragraph = new Paragraph(getPDFTitle(), font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(getTableWidths().length);
        table.setSpacingBefore(10);
        table.setWidths(getTableWidths());
        writeTableHeader(table);
        writeTableData(table, lists);
        document.add(table);

        document.close();
    }

    private void writeTableData(PdfPTable table, List<T> lists) {
        for (T object : lists) {
            for (String field : getFields()) {
                String fieldValue = ObjectUtils.getStringValue(object, field);
                table.addCell(fieldValue);
            }
        }
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setBackgroundColor(Color.BLUE);

        Font font = new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE);

        for (String header : getHeaders()) {
            cell.setPhrase(new Phrase(header, font));
            table.addCell(cell);
        }
    }

    // END PDF
}
