package com.shopme.product;

import com.shopme.category.CategoryService;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.PageInfo;
import com.shopme.common.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import static com.shopme.common.utils.Common.setModelListPage;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    private final CategoryService categoryService;

    @GetMapping("/c/{categoryAlias}")
    public String getProductByCategory(Model model, @PathVariable(name = "categoryAlias") String alias) {
        return listByPage(model, alias, 1);
    }

    @GetMapping("/c/{categoryAlias}/page/{pageNum}")
    public String listByPage(Model model,
                             @PathVariable(name = "categoryAlias") String alias,
                             @PathVariable(name = "pageNum") Integer pageNum
    ) {
        Category category = categoryService.getByAlias(alias);
        if (category == null) {
            return "error/404";
        }
        List<Category> categoryParents = categoryService.getCategoryParents(category);
        PageInfo pageInfo = new PageInfo();
        List<Product> list = service.listByPage(pageInfo, pageNum, category.getId());
        model.addAttribute("pageTitle", category.getName());
        model.addAttribute("category", category);
        model.addAttribute("categoryParents", categoryParents);
        model.addAttribute("products", list);
        setModelListPage(model, "products", null, null, null, pageInfo);
        return "products_by_category";
    }
}
