package com.shopme.customer;

import com.shopme.common.entity.AuthenticationType;
import com.shopme.common.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByEmail(String email);

    Customer findByVerificationCode(String verificationCode);

    @Modifying
    @Query("UPDATE Customer c SET c.enabled = TRUE, c.verificationCode = NULL WHERE c.id = ?1")
    void enable(Long id);

    @Modifying
    @Query("UPDATE Customer c SET c.authenticationType = ?2 WHERE c.id = ?1")
    void updateAuthenticationType(Long id, AuthenticationType type);
}
