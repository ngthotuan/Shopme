package com.shopme.admin.category.export;

import com.shopme.admin.export.CSVExporter;
import com.shopme.common.entity.Category;

public class CategoryCSVExporter extends CSVExporter<Category> {
    @Override
    public String[] getHeaders() {
        return new String[]{"Category ID", "Category Name", "Enabled"};
    }

    @Override
    public String[] getFields() {
        return new String[]{"id", "name", "enabled"};
    }

    @Override
    public String getFilePrefix() {
        return "categories";
    }
}
