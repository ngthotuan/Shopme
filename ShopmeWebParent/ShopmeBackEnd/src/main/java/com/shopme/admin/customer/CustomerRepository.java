package com.shopme.admin.customer;

import com.shopme.common.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT c FROM Customer c WHERE c.email = ?1")
    public Customer findByEmail(String email);

    Long countById(Long id);

    @Query("UPDATE Customer c SET c.enabled = ?2 where c.id = ?1")
    @Modifying
    void updateEnabledStatus(Long id, boolean enabled);

    @Query("SELECT c FROM Customer c WHERE " +
            "CONCAT(c.firstName, ' ', c.lastName, ' ', c.email, ' ', c.addressLine1, ' ', " +
            "c.addressLine2,' ', c.city, ' ', c.state, ' ', c.country.name, ' ', c.postalCode) LIKE %?1%")
    Page<Customer> findAll(String keyword, Pageable pageable);
}
