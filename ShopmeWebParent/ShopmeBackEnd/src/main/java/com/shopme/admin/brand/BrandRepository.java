package com.shopme.admin.brand;

import com.shopme.admin.paging.SearchRepository;
import com.shopme.common.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BrandRepository extends SearchRepository<Brand, Long> {
    Brand findByName(String name);

    @Query("SELECT brand FROM Brand brand WHERE brand.name LIKE %?1%")
    Page<Brand> findAll(String keyword, Pageable pageable);

    long countById(Long id);

    @Query("SELECT new Brand(b.id, b.name) FROM Brand b ORDER BY b.name ASC")
    List<Brand> findAll();
}
