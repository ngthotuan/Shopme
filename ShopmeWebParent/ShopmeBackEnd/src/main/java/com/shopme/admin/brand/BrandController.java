package com.shopme.admin.brand;

import com.shopme.admin.brand.export.BrandCSVExporter;
import com.shopme.admin.brand.export.BrandExcelExporter;
import com.shopme.admin.brand.export.BrandPDFExporter;
import com.shopme.admin.category.CategoryService;
import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static com.shopme.common.utils.Common.setModelListPage;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final BrandService service;
    private final CategoryService categoryService;

    @GetMapping({"", "/"})
    public String listFirstPage(Model model) {
        return listByPage(model, 1, "name", "asc", null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(Model model, @PathVariable(name = "pageNum") Integer pageNum,
                             @RequestParam(name = "sortField", required = false, defaultValue = "name") String sortField,
                             @RequestParam(name = "sortType", required = false, defaultValue = "asc") String sortType,
                             @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {

        PageInfo pageInfo = new PageInfo();
        List<Brand> brands = service.listByPage(pageInfo, pageNum, sortField, sortType, keyword);

        model.addAttribute("brands", brands);
        setModelListPage(model, "brands", sortField, sortType, keyword, pageInfo);
        return "brand/brands";
    }

    @GetMapping("/new")
    public String createCategory(Model model) {
        Brand brand = new Brand();
        List<Category> categories = categoryService.listAll();
        model.addAttribute("pageTitle", "Create New Brand");
        model.addAttribute("brand", brand);
        model.addAttribute("categories", categories);
        return "brand/brand_form";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable(name = "id") Long id,
                       RedirectAttributes redirectAttributes) {
        try {
            Brand entity = service.findById(id);
            List<Category> categories = categoryService.listAll();
            model.addAttribute("pageTitle", "Update Brand " + entity.getId());
            model.addAttribute("brand", entity);
            model.addAttribute("categories", categories);
            return "brand/brand_form";
        } catch (BrandNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
            return "redirect:/brand";
        }
    }


    @PostMapping("/save")
    public String save(Brand entity, @RequestParam(value = "fileImage", required = false) MultipartFile image,
                       RedirectAttributes redirectAttributes) throws IOException {
        if (!image.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            entity.setLogo(fileName);
            Brand savedBrand = service.save(entity);
            String uploadDir = "brand-images/" + savedBrand.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, image);
        } else {
            if (entity.getLogo().isEmpty()) {
                entity.setLogo(null);
            }
            service.save(entity);
        }
        redirectAttributes.addFlashAttribute("message", "The brand has been saved successfully");

        return "redirect:/brands";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("message", String.format("The brand with ID %d has been deleted successfully", id));
            FileUploadUtil.removeDir("brand-images/" + id);
        } catch (BrandNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
        }
        return "redirect:/brands";
    }

    @GetMapping("/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<Brand> list = service.listAll();
        BrandCSVExporter csvExporter = new BrandCSVExporter();
        csvExporter.export(list, response);
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<Brand> categories = service.listAll();
        BrandExcelExporter excelExporter = new BrandExcelExporter();
        excelExporter.export(categories, response);
    }

    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        List<Brand> categories = service.listAll();
        BrandPDFExporter categoryPDFExporter = new BrandPDFExporter();
        categoryPDFExporter.export(categories, response);
    }

}
