package com.shopme.admin.customer;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.paging.PagingAndSortingParam;
import com.shopme.admin.setting.country.CountryService;
import com.shopme.admin.utils.Exporter;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.common.exception.CustomerNotFoundException;
import com.shopme.common.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/customers")
public class CustomerController {
    private final CustomerService service;
    private final CountryService countryService;

    @GetMapping({"", "/"})
    public String listFirstPage() {
        return "redirect:/customers/page/1?sortField=firstName&sortType=asc";
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable Integer pageNum,
                             @PagingAndSortingParam(module = "customers", listName = "customers")
                                     PagingAndSortingHelper helper) {
        service.listByPage(pageNum, helper);
        return "customer/customers";
    }

    @PostMapping("/update")
    public String updateCustomer(Customer customer, RedirectAttributes redirectAttributes) {
        service.update(customer);
        redirectAttributes.addFlashAttribute("message", "The customer has been saved successfully");

        return redirectAfterCustomerModified(customer);
    }

    @GetMapping("/edit/{id}")
    public String updateCustomer(Model model, @PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            Customer customer = service.findById(id);
            List<Country> countries = countryService.findAll();
            model.addAttribute("pageTitle", String.format("Edit Customer ID (%s)", customer.getId()));
            model.addAttribute("customer", customer);
            model.addAttribute("countries", countries);
            return "customer/customer_form";

        } catch (CustomerNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/customers";
        }
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        try {
            Customer customer = service.findById(id);
            model.addAttribute("pageTitle", "Detail Customer");
            model.addAttribute("customer", customer);
            return "customer/customer_detail_dialog";
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
            return "redirect:/customers";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", String.format("The customer with ID %d has been delete successfully", id));
        } catch (CustomerNotFoundException ex) {
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
        }
        return "redirect:/customers";
    }

    @GetMapping("/{id}/enabled/{enabled}")
    public String updateCustomerStatus(@PathVariable Long id,
                                       @PathVariable boolean enabled,
                                       RedirectAttributes redirectAttributes) {
        String status = enabled ? "enabled" : "disabled";
        service.updateCustomerEnabledStatus(id, enabled);
        redirectAttributes.addFlashAttribute("message", String.format("The customer with ID %d has been %s", id, status));
        return "redirect:/customers";
    }

    @GetMapping("/export/csv")
    public void exportCustomerToCSV(HttpServletResponse response) throws IOException {
        List<Customer> list = service.listAll();
        Exporter<Customer> exporter = new CustomerExporter(response);
        exporter.exportCSV(list);
    }

    @GetMapping("/export/excel")
    public void exportCustomerToExcel(HttpServletResponse response) throws IOException {
        List<Customer> list = service.listAll();
        Exporter<Customer> exporter = new CustomerExporter(response);
        exporter.exportExcel(list);
    }

    @GetMapping("/export/pdf")
    public void exportCustomerToPDF(HttpServletResponse response) throws IOException {
        List<Customer> list = service.listAll();
        Exporter<Customer> exporter = new CustomerExporter(response);
        exporter.exportPDF(list);
    }


    private String redirectAfterCustomerModified(Customer customer) {
        String firstPartOfEmail = customer.getEmail().split("@")[0];
        return "redirect:/customers/page/1/?sortField=id&sortType=asc&keyword=" + firstPartOfEmail;
    }
}
