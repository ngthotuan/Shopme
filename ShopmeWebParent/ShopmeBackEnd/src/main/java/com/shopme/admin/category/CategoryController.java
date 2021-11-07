package com.shopme.admin.category;

import com.shopme.admin.category.export.CategoryCSVExporter;
import com.shopme.admin.category.export.CategoryExcelExporter;
import com.shopme.admin.category.export.CategoryPDFExporter;
import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
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
        List<Category> categories = categoryService.listByPage(pageInfo, pageNum, sortField, sortType, keyword);

        model.addAttribute("categories", categories);
        setModelListPage(model, "categories", sortField, sortType, keyword, pageInfo);
        return "category/categories";
    }

    @GetMapping("/new")
    public String createCategory(Model model) {
        Category category = new Category();
        List<Category> categories = categoryService.listAll();
        category.setEnabled(true);
        model.addAttribute("pageTitle", "Create New Category");
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        return "category/category_form";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(Model model, @PathVariable(name = "id") Long id,
                               RedirectAttributes redirectAttributes) {
        try {
            Category category = categoryService.findById(id);
            List<Category> categories = categoryService.listAll();
            model.addAttribute("pageTitle", "Update Category " + category.getId());
            model.addAttribute("category", category);
            model.addAttribute("categories", categories);
            return "category/category_form";
        } catch (CategoryNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
            return "redirect:/categories";
        }
    }

    @PostMapping("/save")
    public String saveCategory(Category category, @RequestParam(value = "fileImage", required = false) MultipartFile image,
                               RedirectAttributes redirectAttributes) throws IOException {
        if (!image.isEmpty()) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(image.getOriginalFilename()));
            category.setImage(fileName);
            Category savedCategory = categoryService.save(category);
            String uploadDir = "category-images/" + savedCategory.getId();
            FileUploadUtil.cleanDir(uploadDir);
            FileUploadUtil.saveFile(uploadDir, fileName, image);
        } else {
            if (category.getImage().isEmpty()) {
                category.setImage(null);
            }
            categoryService.save(category);
        }
        redirectAttributes.addFlashAttribute("message", "The category has been saved successfully");

        return "redirect:/categories";
    }

    @GetMapping("/{id}/enabled/{enabled}")
    @Transactional
    public String updateUserStatus(@PathVariable Long id,
                                   @PathVariable boolean enabled,
                                   RedirectAttributes redirectAttributes) {
        String status = enabled ? "enabled" : "disabled";
        categoryService.updateCategoryEnabledStatus(id, enabled);
        redirectAttributes.addFlashAttribute("message", String.format("The category with ID %d has been %s", id, status));
        return "redirect:/categories";
    }

    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            categoryService.delete(id);
            redirectAttributes.addFlashAttribute("message", String.format("The category with ID %d has been deleted successfully", id));
            FileUploadUtil.removeDir("category-images/" + id);
        } catch (CategoryNotFoundException ex) {
            redirectAttributes.addFlashAttribute("errMessage", ex.getMessage());
        }
        return "redirect:/categories";
    }


    @GetMapping("/export/csv")
    public void exportToCSV(HttpServletResponse response) throws IOException {
        List<Category> categories = categoryService.listAll();
        CategoryCSVExporter csvExporter = new CategoryCSVExporter();
        csvExporter.export(categories, response);
    }

    @GetMapping("/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        List<Category> categories = categoryService.listAll();
        CategoryExcelExporter excelExporter = new CategoryExcelExporter();
        excelExporter.export(categories, response);
    }

    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws IOException {
        List<Category> categories = categoryService.listAll();
        CategoryPDFExporter categoryPDFExporter = new CategoryPDFExporter();
        categoryPDFExporter.export(categories, response);
    }
}
