package com.shopme.admin.product;


import com.shopme.common.entity.PageInfo;
import com.shopme.common.entity.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.shopme.common.utils.Common.createSort;
import static com.shopme.common.utils.Common.setPageInfo;

@Service
@RequiredArgsConstructor
public class ProductService {
    private static final int PER_PAGE = 4;
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
}
