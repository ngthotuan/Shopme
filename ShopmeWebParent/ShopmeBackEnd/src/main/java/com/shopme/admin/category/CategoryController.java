package com.shopme.admin.category;

import com.shopme.admin.user.UserService;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

//    @GetMapping("")
//    public String index(Model model) {
//        List<Category> categories = categoryService.listAll();
//        model.addAttribute("categories", categories);
//        return "category/categories";
//    }

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
}
