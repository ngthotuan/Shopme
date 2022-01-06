package com.shopme.admin.category;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.Category;
import com.shopme.common.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.shopme.common.utils.Common.createSort;

@Service
@RequiredArgsConstructor
public class CategoryService {
    public static final int ROOT_CATEGORY_PER_PAGE = 2;
    private final CategoryRepository categoryRepository;

    public List<Category> listAll(String sortField, String sortType) {
        Sort sort = createSort(sortField, sortType);
        List<Category> rootCategories = categoryRepository.findAllRootCategories(sort);
        return hierarchicalCategories(rootCategories, sortType);
    }

    public List<Category> listAll() {
        return listAll("name", "asc");
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        String keyword = helper.getKeyword();
        Sort sort = helper.getSort();
        Page<Category> pageModel;
        Pageable pageable;

        if (keyword != null && !keyword.isEmpty()) {
            int perPage = 5;
            pageable = PageRequest.of(pageNum - 1, perPage, sort);
            pageModel = categoryRepository.findByKeyword(keyword, pageable);
            helper.updateModelAttributes(pageModel, pageNum, perPage);
        } else {
            pageable = PageRequest.of(pageNum - 1, ROOT_CATEGORY_PER_PAGE, sort);
            pageModel = categoryRepository.findAllRootCategories(pageable);
            List<Category> rootCategories = pageModel.getContent();
            List<Category> categories = hierarchicalCategories(rootCategories, helper.getSortType());
            helper.updateModelAttributes(categories, pageNum, ROOT_CATEGORY_PER_PAGE,
                    pageModel.getTotalElements(), pageModel.getTotalPages());
        }
    }

    public Category save(Category category) {
        if (category.getParent() != null) {
            Category parent = findById(category.getParent().getId());
            String allParents = parent.getParent() != null ? parent.getAllParentIds() : "-";
            allParents += parent.getId() + "-";
            category.setAllParentIds(allParents);
        }
        return categoryRepository.save(category);
    }

    public Category findById(Long id) {
        Optional<Category> opt = categoryRepository.findById(id);
        return opt.orElseThrow(() -> new CategoryNotFoundException("Could not found category with id " + id));
    }

    public String checkDuplicate(Long id, String name, String alias) {
        boolean isCreate = id == null;
        Category category = categoryRepository.findByName(name);
        if (isCreate) {
            if (category != null) {
                return "DuplicateName";
            }
            category = categoryRepository.findByAlias(alias);
            if (category != null) {
                return "DuplicateAlias";
            }
        } else {
            if (category != null && !category.getId().equals(id)) {
                return "DuplicateName";
            }
            category = categoryRepository.findByAlias(alias);
            if (category != null && !category.getId().equals(id)) {
                return "DuplicateAlias";
            }
        }
        return "OK";
    }

    private List<Category> hierarchicalCategories(List<Category> rootCategories, String sortType) {
        List<Category> hierarchicalCategories = new ArrayList<>();
        for (Category category : rootCategories) {
            hierarchicalCategories.add(Category.from(category));
            categoryChildren(hierarchicalCategories, category, 1, sortType);
        }
        return hierarchicalCategories;
    }

    private void categoryChildren(List<Category> categories, Category parent, int level, String sortType) {
        Comparator<Category> comparator = Comparator.comparing(Category::getName);
        if (Objects.equals(sortType, "desc")) {
            comparator = Comparator.comparing(Category::getName).reversed();
        }
        Set<Category> children = new TreeSet<>(comparator);
        children.addAll(parent.getChildren());

        for (Category category : children) {
            String categoryName = "--".repeat(Math.max(0, level)) +
                    category.getName();
            categories.add(Category.from(category, categoryName));
            categoryChildren(categories, category, level + 1, sortType);
        }
    }

    public void updateCategoryEnabledStatus(Long id, boolean status) {
        categoryRepository.updateEnabledStatus(id, status);
    }

    public void delete(Long id) {
        long c = categoryRepository.countById(id);
        if (c == 0) {
            throw new CategoryNotFoundException("Could not found category with id " + id);
        }
        categoryRepository.deleteById(id);
    }
}
