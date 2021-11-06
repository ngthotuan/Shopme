package com.shopme.admin.export;

import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public abstract class CSVExporter<T> extends AbstractExporter {
    public void export(List<T> lists, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, getFilePrefix(), "csv", "text/csv");

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(),
                CsvPreference.EXCEL_PREFERENCE);
        csvBeanWriter.writeHeader(getHeaders());
        for (T obj : lists) {
            csvBeanWriter.write(obj, getFields());
        }
        csvBeanWriter.close();
    }

    public abstract String[] getHeaders();

    public abstract String[] getFields();

    public abstract String getFilePrefix();
}
