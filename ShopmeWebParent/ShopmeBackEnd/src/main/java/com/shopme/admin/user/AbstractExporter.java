package com.shopme.admin.user;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AbstractExporter {
    public void setResponseHeader(HttpServletResponse response, String prefix, String extension, String contentType) throws IOException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_hh-mm-ss");
        String currentDate = dateFormat.format(new Date());
        String fileName = String.format("%s_%s.%s", prefix, currentDate, extension);

        response.setContentType(contentType + "; charset=utf-8");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey, headerValue);
    }
}
