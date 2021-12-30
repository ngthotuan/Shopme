package com.shopme.admin.setting;

import com.shopme.common.entity.Currency;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyRepository repo;

    public List<Currency> listAllByName() {
        return repo.findAllByOrderByNameAsc();
    }

    public Currency findById(Long id) {
        return repo.findById(id).orElse(null);
    }
}
