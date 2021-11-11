package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static com.shopme.common.utils.Common.setModelListPage;

@Controller
@RequiredArgsConstructor
@RequestMapping("/brands")
public class BrandController {
    private final BrandService brandService;

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
        List<Brand> brands = brandService.listByPage(pageInfo, pageNum, sortField, sortType, keyword);

        model.addAttribute("brands", brands);
        setModelListPage(model, "brands", sortField, sortType, keyword, pageInfo);
        return "brand/brands";
    }
}
