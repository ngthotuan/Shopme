package com.shopme.admin.product;


import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.Product;
import com.shopme.common.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final int PER_PAGE = 5;
    private final ProductRepository repo;

    public List<Product> listAll() {
        return repo.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper, long categoryId) {
        String keyword = helper.getKeyword();
        PageRequest pageable = helper.getPageable(pageNum - 1, PER_PAGE);
        Page<Product> pageModel;

        if (keyword != null && !keyword.isEmpty()) {
            if (categoryId != -1) {
                String categoryMatch = String.format("-%s-", categoryId);
                pageModel = repo.findAllByCategoryAndKeyword(categoryId, categoryMatch, keyword, pageable);
            } else {
                pageModel = repo.findAll(keyword, pageable);
            }
        } else {
            if (categoryId != -1) {
                String categoryMatch = String.format("-%s-", categoryId);
                pageModel = repo.findAllByCategory(categoryId, categoryMatch, pageable);
            } else {
                pageModel = repo.findAll(pageable);
            }
        }

        helper.updateModelAttributes(pageModel, pageNum, PER_PAGE);
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setCreatedTime(new Date());
            if (product.getAlias() == null || product.getAlias().isEmpty()) {
                product.setAlias(product.getName().trim().replaceAll("\\s+", "-").toLowerCase());
            } else {
                product.setAlias(product.getAlias().trim().replace(" ", "-"));
            }
        }
        product.setUpdatedTime(new Date());
        return repo.save(product);
    }

    public Product saveProductPrice(Product productInform) {
        Product product = repo.findById(productInform.getId()).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException("Could not found product with id " + productInform.getId());
        }
        product.setCost(productInform.getCost());
        product.setPrice(productInform.getPrice());
        product.setDiscountPercent(productInform.getDiscountPercent());
        return repo.save(product);
    }

    public String checkDuplicate(Long id, String name) {
        boolean isCreate = id == null;
        Product entity = repo.findByName(name);
        if (isCreate) {
            if (entity != null) {
                return "DuplicateName";
            }
        } else {
            if (entity != null && !entity.getId().equals(id)) {
                return "DuplicateName";
            }
        }
        return "OK";
    }

    public void updateEnabledStatus(Long id, boolean status) {
        repo.updateEnabledStatus(id, status);
    }

    public void delete(Long id) {
        long c = repo.countById(id);
        if (c == 0) {
            throw new ProductNotFoundException("Could not found product with id " + id);
        }
        repo.deleteById(id);
    }

    public Product get(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Could not found product with id " + id));
    }
}
