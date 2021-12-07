package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class BrandRepositoryTests {
    private static final Brand acer = new Brand("acer");
    private static final Brand apple = new Brand("apple");
    private static final Brand samsung = new Brand("samsung");
    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testCreateBrand() {
        Brand acerSaved = brandRepository.save(acer);
        assertThat(acerSaved.getId()).isNotNull();

        Brand appleSaved = brandRepository.save(apple);
        assertThat(appleSaved.getId()).isNotNull();

        Brand samsungSaved = brandRepository.save(samsung);
        assertThat(samsungSaved.getId()).isNotNull();
    }

    @Test
    public void testFindAll() {
        assertThat(brandRepository.findAll()).hasSize(3);
    }

    @Test
    public void testGetById() {
        Long id = 1L;
        assertThat(brandRepository.findById(id).get().getName()).isEqualTo("acer");
    }

    @Test
    public void testGetByName() {
        assertThat(brandRepository.findByName("acer").getName()).isEqualTo("acer");
    }

    @Test
    public void testUpdateBrand() {
        Brand acerSaved = brandRepository.findById(1L).get();
        acerSaved.setName("acer updated");
        Brand acerUpdated = brandRepository.save(acerSaved);
        assertThat(acerUpdated.getName()).isEqualTo("acer updated");
    }

    @Test
    public void testDeleteBrand() {
        Brand acerSaved = brandRepository.findById(1L).get();
        brandRepository.delete(acerSaved);
        assertThat(brandRepository.findAll()).hasSize(2);
    }

    // for product module
    @Test
    public void testFindAllList() {
        List<Brand> brands = brandRepository.findAll();
        brands.forEach(System.out::println);
    }
}
