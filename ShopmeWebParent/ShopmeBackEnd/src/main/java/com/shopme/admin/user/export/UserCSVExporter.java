package com.shopme.admin.user.export;

import com.shopme.common.entity.User;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class UserCSVExporter extends AbstractExporter {
    public void export(List<User> users, HttpServletResponse response) throws IOException {
        super.setResponseHeader(response, "users", "csv", "text/csv");

        String[] csvHeaders = {"User ID", "Email", "First Name", "Last Name", "Roles", "Enabled"};
        String[] columnMap = {"id", "email", "firstName", "lastName", "roles", "enabled"};

        ICsvBeanWriter csvBeanWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.EXCEL_PREFERENCE);
        csvBeanWriter.writeHeader(csvHeaders);
        for (User user : users) {
            csvBeanWriter.write(user, columnMap);
        }
        csvBeanWriter.close();
    }
}
