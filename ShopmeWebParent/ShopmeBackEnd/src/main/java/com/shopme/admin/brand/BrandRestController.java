package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
public class BrandRestController {
    private final BrandService service;

    @PostMapping("brands/check_duplicate")
    public String checkDuplicate(@RequestParam(required = false) Long id,
                                 @RequestParam String name) {
        return service.checkDuplicate(id, name);
    }

    @GetMapping("/brands/{id}/categories")
    public List<CategoryDTO> getCategoryByBrandId(@PathVariable(name = "id") Long brandId) {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        try {
            Brand brand = service.findById(brandId);
            Set<Category> categories = brand.getCategories();
            categories.forEach(category -> {
                CategoryDTO categoryDTO = new CategoryDTO();
                BeanUtils.copyProperties(category, categoryDTO);
                categoryDTOList.add(categoryDTO);
            });
        } catch (BrandNotFoundException ex) {
            throw new BrandRestNotFoundException();
        }
        return categoryDTOList;
    }
}
