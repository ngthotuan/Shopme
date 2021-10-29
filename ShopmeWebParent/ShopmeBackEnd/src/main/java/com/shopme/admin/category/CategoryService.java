package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public static final int CATEGORIES_PER_PAGE = 4;

    public List<Category> listAll() {
        return categoryRepository.findAll(Sort.by("name").ascending());
    }

    public Page<Category> listByPage(int pageNum, String sortField, String sortType, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortType.equals("asc") ? sort.ascending() : sort.descending();

        PageRequest pageable = PageRequest.of(pageNum - 1, CATEGORIES_PER_PAGE, sort);

        if (keyword != null) {
            return categoryRepository.findAll(keyword, pageable);
        }
        return categoryRepository.findAll(pageable);
    }

    public List<Category> listCategoriesInForm() {
        List<Category> categoriesInForm = new ArrayList<>();
        List<Category> categories = categoryRepository.findAll();
        for(Category category: categories) {
            if(category.getParent() == null) {
                categoriesInForm.add(Category.copyIdAndName(category));
                printCategoryChildren(categoriesInForm, category, 1);
            }
        }

        return categoriesInForm;
    }

    private void printCategoryChildren(List<Category> categories, Category parent, int level) {
        for(Category category : parent.getChildren()) {
            String categoryName = "--".repeat(Math.max(0, level)) +
                    category.getName();
            categories.add(Category.copyIdAndName(category.getId(), categoryName));

            printCategoryChildren(categories, category, level + 1);
        }
    }
}
