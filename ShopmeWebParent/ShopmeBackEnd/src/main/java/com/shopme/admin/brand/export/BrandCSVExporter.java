package com.shopme.admin.brand.export;

import com.shopme.admin.export.CSVExporter;
import com.shopme.common.entity.Brand;

public class BrandCSVExporter extends CSVExporter<Brand> {
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
