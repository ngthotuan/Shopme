package com.shopme.admin.brand;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BrandRestController {
    private final BrandService service;

    @PostMapping("brands/check_duplicate")
    public String checkDuplicate(@RequestParam(required = false) Long id,
                                 @RequestParam String name) {
        return service.checkDuplicate(id, name);
    }
}
