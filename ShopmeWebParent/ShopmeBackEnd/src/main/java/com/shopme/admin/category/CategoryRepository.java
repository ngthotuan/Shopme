package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT category FROM Category category WHERE category.parent IS NULL")
    List<Category> findAllRootCategories();
    Category findByName(String name);
    Category findByAlias(String alias);
}
