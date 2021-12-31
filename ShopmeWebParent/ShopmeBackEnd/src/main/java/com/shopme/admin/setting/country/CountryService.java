package com.shopme.admin.setting.country;

import com.shopme.common.entity.Country;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryService {
    private final CountryRepository repo;

    public List<Country> findAll() {
        return repo.findAllByOrderByNameAsc();
    }

    public Country save(Country country) {
        return repo.save(country);
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }
}
