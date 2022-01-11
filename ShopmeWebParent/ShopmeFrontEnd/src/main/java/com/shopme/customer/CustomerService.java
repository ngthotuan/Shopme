package com.shopme.customer;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.setting.CountryRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository repo;
    private final CountryRepository countryRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Country> findAllCountries() {
        return countryRepository.findAllByOrderByNameAsc();
    }

    public boolean isEmailUnique(String email) {
        return repo.findByEmail(email) == null;
    }

    public void register(Customer customer) {
        encodePassword(customer);
        customer.setEnabled(false);
        customer.setCreatedTime(new Date());
        String code = RandomString.make(64);
        customer.setVerificationCode(code);
        repo.save(customer);
    }

    public boolean verify(String code) {
        Customer customer = repo.findByVerificationCode(code);
        if (customer == null || customer.isEnabled()) {
            return false;
        }
        customer.setEnabled(true);
        customer.setVerificationCode(null);
        repo.save(customer);
        return true;
    }

    public void updateAuthenticationType(Customer customer, AuthenticationType authenticationType) {
        if (customer.getAuthenticationType() != authenticationType) {
            repo.updateAuthenticationType(customer.getId(), authenticationType);
        }
    }

    private void encodePassword(Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
    }
}
