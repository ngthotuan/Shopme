package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> listAll() {
        List<Category> rootCategories = categoryRepository.findAllRootCategories();
        return hierarchicalCategories(rootCategories);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category findById(Long id) {
        Optional<Category> opt = categoryRepository.findById(id);
        return opt.orElseThrow(() -> new CategoryNotFoundException("Could not found category with id " + id));
    }

    private List<Category> hierarchicalCategories(List<Category> rootCategories) {
        List<Category> hierarchicalCategories = new ArrayList<>();
        for(Category category: rootCategories) {
            hierarchicalCategories.add(Category.from(category));
            categoryChildren(hierarchicalCategories, category, 1);
        }
        return hierarchicalCategories;
    }

    private void categoryChildren(List<Category> categories, Category parent, int level) {
        for(Category category : parent.getChildren()) {
            String categoryName = "--".repeat(Math.max(0, level)) +
                    category.getName();

            categories.add(Category.from(category, categoryName));

            categoryChildren(categories, category, level + 1);
        }
    }
}
