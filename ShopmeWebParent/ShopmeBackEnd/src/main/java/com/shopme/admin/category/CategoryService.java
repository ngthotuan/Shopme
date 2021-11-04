package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public List<Category> listByPage(PageInfo pageInfo, int page, String sortField, String sortType) {
        Sort sort = createSort(sortField, sortType);
        Pageable pageable = PageRequest.of(page - 1, ROOT_CATEGORY_PER_PAGE, sort);

        Page<Category> categoryPage = categoryRepository.findAllRootCategories(pageable);
        List<Category> rootCategories = categoryPage.getContent();
        pageInfo.setTotalPages(categoryPage.getTotalPages());
        pageInfo.setTotalItems(categoryPage.getTotalElements());

        return hierarchicalCategories(rootCategories, sortType);
    }

    private Sort createSort(String sortField, String sortType) {
        Sort sort = Sort.by(sortField).ascending();
        if (Objects.equals(sortType, "desc")) {
            sort = Sort.by(sortField).descending();
        }
        return sort;
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findById(Long id) {
        Optional<Category> opt = categoryRepository.findById(id);
        return opt.orElseThrow(() -> new CategoryNotFoundException("Could not found category with id " + id));
    }

    public String checkDuplicate(Long id, String name, String alias) {
        boolean isCreate = id == null;
        Category category = categoryRepository.findByName(name);
        if(isCreate) {
            if(category != null) {
                return "DuplicateName";
            }
            category = categoryRepository.findByAlias(alias);
            if(category != null) {
                return "DuplicateAlias";
            }
        } else {
            if(category != null && !category.getId().equals(id)) {
                return "DuplicateName";
            }
            category = categoryRepository.findByAlias(alias);
            if(category != null && !category.getId().equals(id)) {
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
