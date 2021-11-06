package com.shopme.admin.category.export;

import com.shopme.admin.export.PDFExporter;
import com.shopme.common.entity.Category;

public class CategoryPDFExporter extends PDFExporter<Category> {

    @Override
    public String getPDFTitle() {
        return "List of categories";
    }

    @Override
    public float[] getTableWidths() {
        return new float[]{3f, 7f, 3f};
    }

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
