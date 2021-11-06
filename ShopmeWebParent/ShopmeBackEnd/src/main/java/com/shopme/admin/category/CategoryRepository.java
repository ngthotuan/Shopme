package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT category FROM Category category WHERE category.parent IS NULL")
    List<Category> findAllRootCategories(Sort sort);

    @Query("SELECT category FROM Category category WHERE category.parent IS NULL")
    Page<Category> findAllRootCategories(Pageable pageable);

    @Query("SELECT category FROM Category category WHERE category.name like %?1%")
    Page<Category> findByKeyword(String name, Pageable pageable);


    Category findByName(String name);

    Category findByAlias(String alias);

    @Query("UPDATE Category category SET category.enabled=?2 WHERE category.id=?1")
    @Modifying
    void updateEnabledStatus(Long id, boolean status);

    long countById(Long id);
}
