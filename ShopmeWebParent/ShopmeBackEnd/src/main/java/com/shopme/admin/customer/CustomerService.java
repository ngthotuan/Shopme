package com.shopme.admin.customer;

import com.shopme.common.entity.Customer;
import com.shopme.common.entity.PageInfo;
import com.shopme.common.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.shopme.common.utils.Common.createSort;
import static com.shopme.common.utils.Common.setPageInfo;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private static final int PER_PAGE = 10;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CustomerRepository repo;

    public List<Customer> listAll() {
        return repo.findAll(Sort.by("firstName").ascending());
    }

    public List<Customer> listByPage(PageInfo pageInfo, int pageNum, String sortField, String sortType, String keyword) {
        Sort sort = createSort(sortField, sortType);

        PageRequest pageable = PageRequest.of(pageNum - 1, PER_PAGE, sort);

        Page<Customer> pageModel;
        if (keyword != null) {
            pageModel = repo.findAll(keyword, pageable);
        } else {
            pageModel = repo.findAll(pageable);
        }

        setPageInfo(pageInfo, pageNum, pageModel, PER_PAGE);

        return pageModel.getContent();
    }


    public Customer update(Customer customer) {
        Customer existingCustomer = findById(customer.getId());
        if (customer.getPassword().isEmpty()) {
            customer.setPassword(existingCustomer.getPassword());
        } else {
            encodePassword(customer);
        }
        return repo.save(customer);
    }

    private void encodePassword(Customer customer) {
        String encodedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Long id, String email) {
        Customer customer = repo.findByEmail(email);
        if (customer == null) {
            return true;
        } else return Objects.equals(customer.getId(), id);
    }

    public Customer findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("Could not found customer with ID " + id));
    }

    public void delete(Long id) {
        Long countById = repo.countById(id);
        if (countById == null || countById == 0) {
            throw new CustomerNotFoundException("Could not found customer with ID " + id);
        }
        repo.deleteById(id);
    }

    @Transactional
    public void updateCustomerEnabledStatus(Long id, boolean status) {
        repo.updateEnabledStatus(id, status);
    }

    public Customer findByEmail(String email) {
        return repo.findByEmail(email);
    }

}
