package com.shopme.admin.product;

import com.shopme.admin.utils.Exporter;
import com.shopme.common.entity.Product;

import javax.servlet.http.HttpServletResponse;

public class ProductExporter extends Exporter<Product> {
    public ProductExporter(HttpServletResponse response) {
        super(response);
    }

    @Override
    public String getPDFTitle() {
        return "List of Products";
    }

    @Override
    public float[] getTableWidths() {
        return new float[]{1f, 3f, 1.5f, 1.5f, 1.5f};
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"ID", "Name", "Brand", "Category", "Enabled"};
    }

    @Override
    public String[] getFields() {
        return new String[]{"id", "name", "brand", "category", "enabled"};
    }

    @Override
    public String getFilePrefix() {
        return "products";
    }
}
