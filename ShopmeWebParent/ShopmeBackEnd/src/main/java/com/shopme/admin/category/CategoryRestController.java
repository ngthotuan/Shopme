package com.shopme.admin.category;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryRestController {
    private final CategoryService categoryService;

    @PostMapping("categories/check_duplicate")
    public String checkDuplicate(@RequestParam(required = false) Long id,
                                 @RequestParam String name,
                                 @RequestParam String alias) {
        return categoryService.checkDuplicate(id, name, alias);
    }
}
