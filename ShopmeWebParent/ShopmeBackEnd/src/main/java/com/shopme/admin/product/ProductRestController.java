package com.shopme.admin.product;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductRestController {
    private final ProductService service;

    @PostMapping("products/check_duplicate")
    public String checkDuplicate(@RequestParam(required = false) Long id,
                                 @RequestParam String name) {
        return service.checkDuplicate(id, name);
    }
}
