package com.shopme.product;

import com.shopme.category.CategoryService;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.PageInfo;
import com.shopme.common.entity.Product;
import com.shopme.common.exception.CategoryNotFoundException;
import com.shopme.common.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.shopme.common.utils.Common.setModelListPage;

@Controller
@RequiredArgsConstructor
public class ProductController {
    private final ProductService service;
    private final CategoryService categoryService;

    @GetMapping("/c/{categoryAlias}")
    public String listFirstPage(Model model, @PathVariable(name = "categoryAlias") String alias) {
        return listByPage(model, alias, 1);
    }

    @GetMapping("/c/{categoryAlias}/page/{pageNum}")
    public String listByPage(Model model,
                             @PathVariable(name = "categoryAlias") String alias,
                             @PathVariable(name = "pageNum") Integer pageNum
    ) {
        try {
            Category category = categoryService.getByAlias(alias);
            List<Category> categoryParents = categoryService.getCategoryParents(category);
            PageInfo pageInfo = new PageInfo();
            List<Product> list = service.listByPage(pageInfo, pageNum, category.getId());
            model.addAttribute("pageTitle", category.getName());
            model.addAttribute("category", category);
            model.addAttribute("categoryParents", categoryParents);
            model.addAttribute("products", list);
            setModelListPage(model, "products", null, null, null, pageInfo);
            return "product/products_by_category";
        } catch (CategoryNotFoundException e) {
            return "error/404";
        }
    }

    @GetMapping("/p/{productAlias}")
    public String viewProductDetail(Model model, @PathVariable(name = "productAlias") String alias) {
        try {
            Product product = service.findProductByAlias(alias);
            List<Category> categoryParents = categoryService.getCategoryParents(product.getCategory());
            model.addAttribute("pageTitle", product.getShortName());
            model.addAttribute("product", product);
            model.addAttribute("categoryParents", categoryParents);
            return "product/product_detail";
        } catch (ProductNotFoundException e) {
            return "error/404";
        }
    }

    @GetMapping("/search")
    public String searchFirstPage(Model model, @RequestParam(name = "keyword") String keyword) {
        return searchByPage(model, keyword, 1);
    }

    @GetMapping("/search/page/{pageNum}")
    public String searchByPage(Model model, @RequestParam(name = "keyword") String keyword,
                               @PathVariable(name = "pageNum") Integer pageNum) {
        PageInfo pageInfo = new PageInfo();
        List<Product> list = service.searchByPage(pageInfo, pageNum, keyword);
        model.addAttribute("pageTitle", "Search: " + keyword);
        model.addAttribute("products", list);
        setModelListPage(model, "products", null, null, null, pageInfo);
        model.addAttribute("pageTitle", keyword + " - Search Result");

        model.addAttribute("keyword", keyword);
        model.addAttribute("listResult", list);

        return "product/search_result";
    }

}
