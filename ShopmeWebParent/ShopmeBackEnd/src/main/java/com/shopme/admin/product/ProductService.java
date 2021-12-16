package com.shopme.admin.product;


import com.shopme.common.entity.PageInfo;
import com.shopme.common.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.shopme.common.utils.Common.createSort;
import static com.shopme.common.utils.Common.setPageInfo;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final int PER_PAGE = 5;
    private final ProductRepository repo;

    public List<Product> listAll() {
        return repo.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public List<Product> listByPage(PageInfo pageInfo, int pageNum, String sortField, String sortType, String keyword) {
        Sort sort = createSort(sortField, sortType);

        PageRequest pageable = PageRequest.of(pageNum - 1, PER_PAGE, sort);

        Page<Product> pageModel;
        if (keyword != null) {
            pageModel = repo.findAll(keyword, pageable);
        } else {
            pageModel = repo.findAll(pageable);
        }

        setPageInfo(pageInfo, pageNum, pageModel, PER_PAGE);

        return pageModel.getContent();
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
