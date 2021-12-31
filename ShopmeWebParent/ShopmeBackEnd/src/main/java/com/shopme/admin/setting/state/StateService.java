package com.shopme.admin.setting.state;

import com.shopme.common.entity.Country;
import com.shopme.common.entity.State;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StateService {
    private final StateRepository repo;

    public List<State> findAllByCountryId(Long countryId) {
        Country country = new Country(countryId);
        return repo.findByCountryOrderByNameAsc(country);
    }

    public State save(State state) {
        return repo.save(state);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
