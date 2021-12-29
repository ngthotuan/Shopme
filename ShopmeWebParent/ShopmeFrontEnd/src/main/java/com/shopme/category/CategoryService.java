package com.shopme.category;

import com.shopme.common.entity.Category;
import com.shopme.common.exception.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository repo;

    public List<Category> listNoChildrenCategories() {
        List<Category> listNoChildrenCategories = new ArrayList<>();

        List<Category> listEnabledCategories = repo.findAllEnabled();

        listEnabledCategories.forEach(category -> {
            Set<Category> children = category.getChildren();
            if (children == null || children.size() == 0) {
                listNoChildrenCategories.add(category);
            }
        });

        return listNoChildrenCategories;
    }

    public Category getByAlias(String alias) {
        Category category = repo.findByAliasEnabled(alias);
        if (category == null) {
            throw new CategoryNotFoundException("Could not find category with alias: " + alias);
        }
        return category;
    }

    public List<Category> getCategoryParents(Category category) {
        List<Category> parents = new ArrayList<>();
        parents.add(category);
        Category parent = category.getParent();
        while (parent != null) {
            parents.add(parent);
            parent = parent.getParent();
        }
        Collections.reverse(parents);
        return parents;
    }
}
