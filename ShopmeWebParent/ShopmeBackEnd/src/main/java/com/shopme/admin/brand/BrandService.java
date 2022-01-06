package com.shopme.admin.brand;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.Brand;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BrandService {
    private final BrandRepository repo;
    private static final int PER_PAGE = 4;

    public List<Brand> listAll() {
        return repo.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(repo, pageNum, PER_PAGE);
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
