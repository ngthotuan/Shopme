package com.shopme.customer;

import com.shopme.common.entity.Country;
import com.shopme.setting.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repo;
    private final CountryRepository countryRepository;

    public List<Country> findAllCountries() {
        return countryRepository.findAllByOrderByNameAsc();
    }

    public boolean isEmailUnique(String email) {
        return repo.findByEmail(email) == null;
    }
}
