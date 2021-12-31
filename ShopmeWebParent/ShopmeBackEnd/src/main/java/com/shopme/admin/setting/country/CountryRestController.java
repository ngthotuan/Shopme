package com.shopme.admin.setting.country;

import com.shopme.common.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/countries")
public class CountryRestController {
    private final CountryService service;

    @GetMapping("/list")
    public List<Country> list() {
        return service.findAll();
    }

    @PostMapping("/save")
    public Country create(@RequestBody Country country) {
        return service.save(country);
    }

    @GetMapping("/delete/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.deleteById(id);
    }

}
