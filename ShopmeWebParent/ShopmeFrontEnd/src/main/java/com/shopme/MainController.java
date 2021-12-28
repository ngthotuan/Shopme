package com.shopme;

import com.shopme.category.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final CategoryService categoryService;

    @GetMapping("")
    public String viewHomePage(Model model) {
        model.addAttribute("categories", categoryService.listNoChildrenCategories());
        return "index";
    }
}
