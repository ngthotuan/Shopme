package com.shopme.admin.user.export;

import com.shopme.admin.export.PDFExporter;
import com.shopme.common.entity.User;

public class UserPDFExporter extends PDFExporter<User> {

    @Override
    public String[] getHeaders() {
        return new String[]{"User ID", "Email", "First Name", "Last Name", "Roles", "Enabled"};
    }

    @Override
    public String[] getFields() {
        return new String[]{"id", "email", "firstName", "lastName", "roles", "enabled"};
    }

    @Override
    public String getFilePrefix() {
        return "users";
    }

    @Override
    public String getPDFTitle() {
        return "List of users";
    }

    @Override
    public float[] getTableWidths() {
        return new float[]{1f, 3.5f, 3f, 3f, 3f, 2f};
    }

}
