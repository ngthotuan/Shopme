package com.shopme.customer;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.Country;
import com.shopme.common.entity.Customer;
import com.shopme.setting.CountryRepository;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        customer.setAuthenticationType(AuthenticationType.DATABASE);
        repo.save(customer);
        return true;
    }

    @Transactional
    public void updateAuthenticationType(Customer customer, AuthenticationType authenticationType) {
        if (customer.getAuthenticationType() != authenticationType) {
            repo.updateAuthenticationType(customer.getId(), authenticationType);
        }
    }

    public Customer findByEmail(String email) {
        return repo.findByEmail(email);
    }

    public void addNewCustomerUponOAuthLogin(String name, String email, AuthenticationType authenticationType) {
        Customer customer = new Customer();
        customer.setEmail(email);
        setName(name, customer);

        customer.setEnabled(true);
        customer.setCreatedTime(new Date());
        customer.setAuthenticationType(authenticationType);
        customer.setPassword("");
        customer.setAddressLine1("");
        customer.setCity("");
        customer.setState("");
        customer.setPhoneNumber("");
        customer.setPostalCode("");
        repo.save(customer);
    }

    private void setName(String name, Customer customer) {
        String[] nameArray = name.split(" ");
        if (nameArray.length < 2) {
            customer.setFirstName(name);
            customer.setLastName("");
        } else {
            String firstName = nameArray[0];
            customer.setFirstName(firstName);

            String lastName = name.replaceFirst(firstName, "");
            customer.setLastName(lastName);
        }
    }

    private void encodePassword(Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
    }
}
