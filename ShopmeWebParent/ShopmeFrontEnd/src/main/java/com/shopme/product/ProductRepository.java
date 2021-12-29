package com.shopme.product;

import com.shopme.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.enabled = TRUE " +
            "AND (p.category.id = ?1 OR p.category.allParentIds LIKE %?2%) " +
            "ORDER BY p.name ASC")
    Page<Product> findProductByCategory(Long categoryId, String categoryMatch, Pageable pageable);

    Product findByAlias(String alias);
}
