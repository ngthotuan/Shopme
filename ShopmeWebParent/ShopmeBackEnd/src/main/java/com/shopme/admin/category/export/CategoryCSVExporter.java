package com.shopme.admin.category.export;

import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.Category;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CategoryCSVExporter extends AbstractExporter {
    public void export(List<Category> categories, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "categories", "csv", "text/csv");

        String[] csvHeaders = {"Category ID", "Category Name", "Enabled"};
        String[] columnMap = {"id", "name", "enabled"};

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);
        csvBeanWriter.writeHeader(csvHeaders);
        for (Category category : categories) {
            category.setName(category.getName().replace("--", "  "));
            csvBeanWriter.write(category, columnMap);
        }
        csvBeanWriter.close();
    }
}
