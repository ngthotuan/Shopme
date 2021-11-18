package com.shopme.admin.brand;

import com.shopme.admin.utils.Exporter;
import com.shopme.common.entity.Brand;

import javax.servlet.http.HttpServletResponse;

public class BrandExporter extends Exporter<Brand> {
    public BrandExporter(HttpServletResponse response) {
        super(response);
    }

    @Override
    public String getPDFTitle() {
        return "List of Brands";
    }

    @Override
    public float[] getTableWidths() {
        return new float[]{3f, 3f, 7f};
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"Brand ID", "Brand Name", "Categories"};
    }

    @Override
    public String[] getFields() {
        return new String[]{"id", "name", "categories"};
    }

    @Override
    public String getFilePrefix() {
        return "brands";
    }
}
