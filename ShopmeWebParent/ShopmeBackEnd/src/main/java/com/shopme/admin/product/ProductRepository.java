package com.shopme.admin.product;

import com.shopme.common.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT product FROM Product product WHERE product.name LIKE %?1%"
            + " OR product.alias LIKE %?1%"
            + " OR product.shortDescription LIKE %?1%"
            + " OR product.fullDescription LIKE %?1%"
            + " OR product.brand.name LIKE %?1%"
            + " OR product.category.name LIKE %?1%"
    )
    Page<Product> findAll(String keyword, Pageable pageable);

    @Query("SELECT product FROM Product product WHERE product.category.id = ?1"
            + " OR product.category.allParentIds LIKE %?2%"
    )
    Page<Product> findAllByCategory(Long categoryId, String categoryMatch, Pageable pageable);

    @Query("SELECT product FROM Product product " +
            "WHERE (product.category.id = ?1 OR product.category.allParentIds LIKE %?2%)"
            + " AND (product.name LIKE %?3% "
            + " OR product.alias LIKE %?3% "
            + " OR product.shortDescription LIKE %?3% "
            + " OR product.fullDescription LIKE %?3% "
            + " OR product.brand.name LIKE %?3% "
            + " OR product.category.name LIKE %?3%)"
    )
    Page<Product> findAllByCategoryAndKeyword(Long categoryId, String categoryMatch, String keyword, Pageable pageable);

    Product findByName(String name);

    @Query("UPDATE Product product SET product.enabled=?2 WHERE product.id=?1")
    @Modifying
    void updateEnabledStatus(Long id, boolean status);

    long countById(Long id);

}
