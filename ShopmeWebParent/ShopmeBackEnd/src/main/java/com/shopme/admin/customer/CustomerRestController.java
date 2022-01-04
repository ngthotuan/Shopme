package com.shopme.admin.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerRestController {
    private final CustomerService service;

    @PostMapping("customers/check_email")
    public boolean checkDuplicateEmail(@RequestParam(required = false) Long id, @RequestParam String email) {
        return service.isEmailUnique(id, email);
    }
}
