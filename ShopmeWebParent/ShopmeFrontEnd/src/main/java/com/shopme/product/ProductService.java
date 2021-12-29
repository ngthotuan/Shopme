package com.shopme.product;

import com.shopme.common.entity.PageInfo;
import com.shopme.common.entity.Product;
import com.shopme.common.exception.ProductNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shopme.common.utils.Common.setPageInfo;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final int PER_PAGE = 12;
    private final ProductRepository repo;

    public List<Product> listByPage(PageInfo pageInfo, int pageNum, Long categoryId) {
        PageRequest pageable = PageRequest.of(pageNum - 1, PER_PAGE);

        Page<Product> pageModel;
        String categoryMatch = String.format("-%s-", categoryId);
        pageModel = repo.findProductByCategory(categoryId, categoryMatch, pageable);
        setPageInfo(pageInfo, pageNum, pageModel, PER_PAGE);

        return pageModel.getContent();
    }

    public Product findProductByAlias(String alias) {
        Product product = repo.findByAlias(alias);
        if (product == null) {
            throw new ProductNotFoundException("Could not find product with alias: " + alias);
        }
        return product;
    }

}
