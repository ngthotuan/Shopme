package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

import static com.shopme.common.utils.Common.setPageInfo;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository repo;
    private final int PER_PAGE = 10;

    public List<Brand> listByPage(PageInfo pageInfo, int pageNum, String sortField, String sortType, String keyword) {
        Sort sort = createSort(sortField, sortType);

        PageRequest pageable = PageRequest.of(pageNum - 1, PER_PAGE, sort);

        Page<Brand> pageModel;
        if (keyword != null) {
            pageModel = repo.findAll(keyword, pageable);
        } else {
            pageModel = repo.findAll(pageable);
        }

        long startCount = (long) (pageNum - 1) * PER_PAGE + 1;
        long endCount = startCount + PER_PAGE - 1;
        if (endCount > pageModel.getTotalElements()) {
            endCount = pageModel.getTotalElements();
        }
        pageInfo.setStartCount(startCount);
        pageInfo.setEndCount(endCount);
        pageInfo.setCurrentPage(pageNum);
        pageInfo.setTotalPages(pageModel.getTotalPages());
        pageInfo.setTotalItems(pageModel.getTotalElements());

        setPageInfo(pageInfo, pageNum, pageModel, PER_PAGE);

        return pageModel.getContent();
    }

    private Sort createSort(String sortField, String sortType) {
        Sort sort = Sort.by(sortField).ascending();
        if (Objects.equals(sortType, "desc")) {
            sort = Sort.by(sortField).descending();
        }
        return sort;
    }
}
