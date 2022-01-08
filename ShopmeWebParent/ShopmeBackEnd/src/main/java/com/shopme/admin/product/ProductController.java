package com.shopme.admin.product;

import com.shopme.admin.brand.BrandService;
import com.shopme.admin.category.CategoryService;
import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.admin.paging.PagingAndSortingParam;
import com.shopme.admin.security.ShopmeUserDetails;
import com.shopme.admin.utils.Exporter;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;
import com.shopme.common.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.shopme.admin.product.ProductSaveHelper.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService service;
    private final BrandService brandService;
    private final CategoryService categoryService;

    @GetMapping({"", "/"})
    public String listFirstPage() {
        return "redirect:/products/page/1?sortField=name&sortType=asc";
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(@PathVariable Integer pageNum,
                             @PagingAndSortingParam(moduleURL = "/products", listName = "products") PagingAndSortingHelper helper,
                             @RequestParam(name = "categoryId", required = false, defaultValue = "-1") Long categoryId,
                             Model model) {
        service.listByPage(pageNum, helper, categoryId);
        List<Category> categories = categoryService.listAll();
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("categories", categories);
        return "product/products";
    }

    @GetMapping("/new")
    public String create(Model model) {
        Product product = new Product();
        product.setEnabled(true);
        product.setInStock(true);
        List<Brand> brands = brandService.listAll();

        model.addAttribute("pageTitle", "Create New Product");
        model.addAttribute("product", product);
        model.addAttribute("brands", brands);
        return "product/product_form";
    }

    @PostMapping("/save")
    public String save(Product entity, RedirectAttributes redirectAttributes,
                       @RequestParam(value = "fileImage", required = false) MultipartFile image,
                       @RequestParam(value = "extraImage", required = false) List<MultipartFile> extraImages,
                       @RequestParam(value = "detailIDs", required = false) String[] detailIDs,
                       @RequestParam(value = "detailNames", required = false) String[] detailNames,
                       @RequestParam(value = "detailValues", required = false) String[] detailValues,
                       @RequestParam(name = "imageIDs", required = false) String[] imageIDs,
                       @RequestParam(name = "imageNames", required = false) String[] imageNames,
                       @AuthenticationPrincipal ShopmeUserDetails userDetails
    ) throws IOException {
        if (userDetails.hasRole("Salesperson") && !userDetails.hasAnyRole("Admin", "Editor")) {
            service.saveProductPrice(entity);
        } else {
            setMainImage(entity, image);
            setExistingExtraImageNames(entity, imageIDs, imageNames);
            setExtraImages(entity, extraImages);
            setDetails(entity, detailIDs, detailNames, detailValues);

            Product saved = service.save(entity);
            saveImages(image, extraImages, saved);
            removeUnusedImages(saved);
        }

        redirectAttributes.addFlashAttribute("message", "The product has been saved successfully");
        return "redirect:/products";
    }

    @GetMapping("/{id}/enabled/{enabled}")
    @Transactional
    public String updateStatus(@PathVariable Long id,
                               @PathVariable boolean enabled,
                               RedirectAttributes redirectAttributes) {
        String status = enabled ? "enabled" : "disabled";
        service.updateEnabledStatus(id, enabled);
        redirectAttributes.addFlashAttribute("message",
                String.format("The product with ID %d has been %s", id, status));
        return "redirect:/products";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message",
                    String.format("The product with ID %d has been deleted successfully", id));
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
        }
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        try {
            Product product = service.get(id);
            List<Brand> brands = brandService.listAll();

            model.addAttribute("pageTitle", "Edit Product");
            model.addAttribute("product", product);
            model.addAttribute("brands", brands);
            return "product/product_form";
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
            return "redirect:/products";
        }
    }

    @GetMapping("/detail/{id}")
    public String detail(@PathVariable Long id, RedirectAttributes redirectAttributes, Model model) {
        try {
            Product product = service.get(id);
            model.addAttribute("pageTitle", "Detail Product");
            model.addAttribute("product", product);
            return "product/product_detail_dialog";
        } catch (ProductNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
            return "redirect:/products";
        }
    }

    @GetMapping("/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<Product> list = service.listAll();
        Exporter<Product> exporter = new ProductExporter(response);
        exporter.exportCSV(list);
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<Product> list = service.listAll();
        Exporter<Product> exporter = new ProductExporter(response);
        exporter.exportExcel(list);
    }

    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        List<Product> list = service.listAll();
        Exporter<Product> exporter = new ProductExporter(response);
        exporter.exportPDF(list);
    }

}
