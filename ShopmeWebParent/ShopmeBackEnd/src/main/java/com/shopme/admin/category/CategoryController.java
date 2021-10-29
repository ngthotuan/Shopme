package com.shopme.admin.category;

import com.shopme.admin.utils.FileUploadUtil;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public String listAll(Model model) {
        return listByPage(model, 1, "name", "asc", null);
    }

    @GetMapping("/page/{pageNum}")
    public String listByPage(Model model, @PathVariable Integer pageNum,
                             @RequestParam String sortField, @RequestParam String sortType,
                             @RequestParam(required = false) String keyword) {
        Page<Category> categoryPage = categoryService.listByPage(pageNum, sortField, sortType, keyword);
        List<Category> categories = categoryPage.getContent();

        long startCount = (long) (pageNum - 1) * CategoryService.CATEGORIES_PER_PAGE + 1;
        long endCount = pageNum * CategoryService.CATEGORIES_PER_PAGE;
        if (endCount > categoryPage.getTotalElements()) {
            endCount = categoryPage.getTotalElements();
        }
        String sortTypeReverse = sortType.equals("asc") ? "desc" : "asc";

        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", categoryPage.getTotalElements());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", categoryPage.getTotalPages());
        model.addAttribute("categories", categories);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortType", sortType);
        model.addAttribute("sortTypeReverse", sortTypeReverse);
        model.addAttribute("keyword", keyword);
        return "category/categories";
    }

    @GetMapping("/new")
    public String createCategory(Model model) {
        Category category = new Category();
        List<Category> categories = categoryService.listCategoriesInForm();
        category.setEnabled(true);
        model.addAttribute("pageTitle", "Create New Category");
        model.addAttribute("category", category);
        model.addAttribute("categories", categories);
        return "category/category_form";
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

}
