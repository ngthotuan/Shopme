package com.shopme.admin.user.controller;

import com.shopme.admin.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserRestController {
    private final UserService userService;

    @PostMapping("/users/check_email")
    public boolean checkDuplicateEmail(@RequestParam(required = false) Long id, @RequestParam String email) {
        return userService.isEmailUnique(id, email);
    }
}
