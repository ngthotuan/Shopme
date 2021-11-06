package com.shopme.admin.export;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.admin.utils.ObjectUtils;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;


public abstract class PDFExporter<T> extends AbstractExporter {
    public void export(List<T> lists, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, getFilePrefix(), "pdf", "application/pdf");

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

    public abstract String getPDFTitle();

    public abstract float[] getTableWidths();

    public abstract String[] getHeaders();

    public abstract String[] getFields();

    public abstract String getFilePrefix();
}
