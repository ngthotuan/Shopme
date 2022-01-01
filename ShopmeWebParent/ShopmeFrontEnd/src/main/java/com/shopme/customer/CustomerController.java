package com.shopme.customer;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        List<Country> countries = service.findAllCountries();
        model.addAttribute("customer", new Customer());
        model.addAttribute("countries", countries);
        return "register/register_form";
    }

}
