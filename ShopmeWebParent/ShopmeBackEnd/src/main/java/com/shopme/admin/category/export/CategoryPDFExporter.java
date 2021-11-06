package com.shopme.admin.category.export;

import com.lowagie.text.Font;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.Category;

import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class CategoryPDFExporter extends AbstractExporter {
    public void export(List<Category> categories, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "categories", "pdf", "application/pdf");

        Document document = new Document();
        PdfWriter.getInstance(document, response.getOutputStream());

        document.open();

        Font font = new Font();
        font.setFamily(FontFactory.HELVETICA_BOLD);
        font.setColor(Color.BLUE);
        font.setSize(20f);
        Paragraph paragraph = new Paragraph("List of Categories", font);
        paragraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(paragraph);

        PdfPTable table = new PdfPTable(3);
        table.setSpacingBefore(10);
        table.setWidths(new float[]{3f, 7f, 3f});
        writeTableHeader(table);
        writeTableData(table, categories);
        document.add(table);

        document.close();
    }

    private void writeTableData(PdfPTable table, List<Category> categories) {
        for (Category category : categories) {
            table.addCell(String.valueOf(category.getId()));
            table.addCell(category.getName().replace("--", "  "));
            table.addCell(String.valueOf(category.isEnabled()));
        }
    }

    private void writeTableHeader(PdfPTable table) {
        PdfPCell cell = new PdfPCell();
        cell.setPadding(5);
        cell.setBackgroundColor(Color.BLUE);

        Font font = new Font(Font.HELVETICA, 10, Font.BOLD, Color.WHITE);

        cell.setPhrase(new Phrase("Category ID", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Category Name", font));
        table.addCell(cell);
        cell.setPhrase(new Phrase("Enabled", font));
        table.addCell(cell);
    }

}
