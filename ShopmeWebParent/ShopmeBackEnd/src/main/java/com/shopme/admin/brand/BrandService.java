package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.PageInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.shopme.common.utils.Common.createSort;
import static com.shopme.common.utils.Common.setPageInfo;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository repo;
    private static final int PER_PAGE = 4;

    public List<Brand> listByPage(PageInfo pageInfo, int pageNum, String sortField, String sortType, String keyword) {
        Sort sort = createSort(sortField, sortType);

        PageRequest pageable = PageRequest.of(pageNum - 1, PER_PAGE, sort);

        Page<Brand> pageModel;
        if (keyword != null) {
            pageModel = repo.findAll(keyword, pageable);
        } else {
            pageModel = repo.findAll(pageable);
        }

        setPageInfo(pageInfo, pageNum, pageModel, PER_PAGE);

        return pageModel.getContent();
    }

    public Brand save(Brand entity) {
        return repo.save(entity);
    }

    public Brand findById(Long id) {
        Optional<Brand> opt = repo.findById(id);
        return opt.orElseThrow(() -> new BrandNotFoundException("Could not found brand with id " + id));
    }

    public String checkDuplicate(Long id, String name) {
        boolean isCreate = id == null;
        Brand entity = repo.findByName(name);
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

    public void delete(Long id) {
        long c = repo.countById(id);
        if (c == 0) {
            throw new BrandNotFoundException("Could not found brand with id " + id);
        }
        repo.deleteById(id);
    }
}
