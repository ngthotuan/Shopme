package com.shopme.admin.brand.export;

import com.shopme.admin.export.PDFExporter;
import com.shopme.common.entity.Brand;

public class BrandPDFExporter extends PDFExporter<Brand> {

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
        return new String[]{"Brand ID", "Brand Name", "categories"};
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
