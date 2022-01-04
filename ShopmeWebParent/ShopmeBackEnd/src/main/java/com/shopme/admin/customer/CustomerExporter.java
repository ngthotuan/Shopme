package com.shopme.admin.customer;

import com.shopme.admin.utils.Exporter;
import com.shopme.common.entity.Customer;

import javax.servlet.http.HttpServletResponse;

public class CustomerExporter extends Exporter<Customer> {
    public CustomerExporter(HttpServletResponse response) {
        super(response);
    }

    @Override
    public String[] getHeaders() {
        return new String[]{"ID", "First Name", "Last Name", "Email", "City", "State", "Country", "Enabled"};
    }

    @Override
    public String[] getFields() {
        return new String[]{"id", "firstName", "lastName", "email", "city", "state", "country", "enabled"};
    }

    @Override
    public String getFilePrefix() {
        return "customers";
    }

    @Override
    public String getPDFTitle() {
        return "List of customers";
    }

    @Override
    public float[] getTableWidths() {
        return new float[]{1f, 3f, 3f, 3.5f, 3f, 3f, 3f, 2f};
    }
}
