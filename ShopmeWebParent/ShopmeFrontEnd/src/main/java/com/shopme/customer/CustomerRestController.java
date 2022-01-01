package com.shopme.customer;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CustomerRestController {
    private final CustomerService service;

    @PostMapping("/customer/check_email")
    public boolean checkDuplicateEmail(@RequestParam("email") String email) {
        return service.isEmailUnique(email);
    }
}
