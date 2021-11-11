package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByName(String name);

    @Query("SELECT brand FROM Brand brand WHERE brand.name LIKE %?1%")
    Page<Brand> findAll(String keyword, Pageable pageable);

    long countById(Long id);
}
