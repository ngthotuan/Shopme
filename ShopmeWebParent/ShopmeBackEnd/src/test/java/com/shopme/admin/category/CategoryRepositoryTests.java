package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import com.shopme.common.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class CategoryRepositoryTests {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateRootCategory() {
        Category category = new Category();
        category.setName("Computer");
        category.setAlias("computer");
        Category categorySaved = categoryRepository.save(category);

        Assertions.assertThat(categorySaved.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategory() {
        Category category = new Category();
        category.setName("Laptop");
        category.setAlias("laptop");

        Category parent = new Category();
        parent.setId(1L);
        category.setParent(parent);

        Category categorySaved = categoryRepository.save(category);
        Assertions.assertThat(categorySaved.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetCategoryById(){
        Category category = categoryRepository.findById(1L).get();
        Set<Category> children = category.getChildren();
        children.forEach(c -> System.out.println(c.getName()));

        Assertions.assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testPrintHierarchicalCategories(){
        List<Category> categories = categoryRepository.findAll();
        for(Category category: categories) {
            if(category.getParent() == null) {
                System.out.println(category.getName());
                printCategoryChildren(category, 1);
            }
        }
    }
    private void printCategoryChildren(Category parent, int level) {
        for(Category category : parent.getChildren()) {
            for(int i = 0; i < level; i++) {
                System.out.print("--");
            }
            System.out.println(category.getName());
            printCategoryChildren(category, level + 1);
        }
    }

    @Test
    public void testListRootCategories() {
        List<Category> categories= categoryRepository.findAllRootCategories();
        categories.forEach(c -> System.out.println(c.getName()));
    }

}
