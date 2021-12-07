package com.shopme.admin.product;

import com.shopme.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT product FROM Product product WHERE product.name LIKE %?1%")
    Page<Product> findAll(String keyword, Pageable pageable);

    Product findByName(String name);

    @Query("UPDATE Product product SET product.enabled=?2 WHERE product.id=?1")
    @Modifying
    void updateEnabledStatus(Long id, boolean status);

    long countById(Long id);
}
