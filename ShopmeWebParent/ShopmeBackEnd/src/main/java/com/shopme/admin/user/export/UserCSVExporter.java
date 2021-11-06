package com.shopme.admin.user.export;

import com.shopme.admin.export.CSVExporter;
import com.shopme.common.entity.User;

public class UserCSVExporter extends CSVExporter<User> {
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
}
