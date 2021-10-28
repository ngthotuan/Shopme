package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT category FROM Category category WHERE CONCAT(category.id, ' ', category.name, ' ', category.alias)" +
            "LIKE %?1%")
    Page<Category> findAll(String keyword, Pageable pageable);
}
