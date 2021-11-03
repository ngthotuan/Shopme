package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith({MockitoExtension.class})
public class CategoryServiceTests {
    @Mock
    private CategoryRepository categoryRepository;
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryService = new CategoryService(categoryRepository);
    }

    @Test
    public void newCategoryDuplicateName() {
        Long id = null;
        String name = "Computers";
        String alias = "computers";
        Category category = new Category(id, name, alias);

        given(categoryRepository.findByName(name)).willReturn(category);

        String result = categoryService.checkDuplicate(id, name, alias);
        assertThat(result).isEqualTo("DuplicateName");
    }

    @Test
    public void updateCategoryDuplicateName() {
        Long id = 3L;
        String name = "Computers";
        String alias = "computers";
        Category category = new Category(id, name, alias);

        given(categoryRepository.findByName(name)).willReturn(category);

        String result = categoryService.checkDuplicate(id, name, alias);
        assertThat(result).isEqualTo("OK");
    }

    @Test
    public void newCategoryDuplicateAlias() {
        Long id = null;
        String name = "Computers";
        String alias = "computers";
        Category category = new Category(id, name, alias);

        given(categoryRepository.findByAlias(alias)).willReturn(category);

        String result = categoryService.checkDuplicate(id, name, alias);
        assertThat(result).isEqualTo("DuplicateAlias");
    }

    @Test
    public void updateCategoryDuplicateAlias() {
        Long id = 3L;
        String name = "Computers";
        String alias = "computers";
        Category category = new Category(id, name, alias);

        given(categoryRepository.findByAlias(alias)).willReturn(category);

        String result = categoryService.checkDuplicate(id, name, alias);
        assertThat(result).isEqualTo("OK");
    }
}
