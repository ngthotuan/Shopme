package com.shopme.admin.category;

import com.shopme.admin.utils.Exporter;
import com.shopme.common.entity.Category;

import javax.servlet.http.HttpServletResponse;

public class CategoryExporter extends Exporter<Category> {
    public CategoryExporter(HttpServletResponse response) {
        super(response);
    }


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
