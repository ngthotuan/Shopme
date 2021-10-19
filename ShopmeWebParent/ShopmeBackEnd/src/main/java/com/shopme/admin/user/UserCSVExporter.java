package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class UserCSVExporter {
    public void export(List<User> users, HttpServletResponse response) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
        String currentDate = dateFormat.format(new Date());
        String fileName = String.format("users_%s.csv", currentDate);

        response.setContentType("text/csv; charset=utf-8");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);

        String[] csvHeaders = {"User ID", "Email", "First Name", "LastName", "Roles", "Enabled"};
        String[] columnMap = {"id", "email", "firstName", "lastName", "roles", "enabled"};

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);
        csvBeanWriter.writeHeader(csvHeaders);
        for (User user : users) {
            csvBeanWriter.write(user, columnMap);
        }
        csvBeanWriter.close();
    }
}
