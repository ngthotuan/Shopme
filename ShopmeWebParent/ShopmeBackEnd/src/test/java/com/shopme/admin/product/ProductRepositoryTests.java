package com.shopme.admin.product;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateProduct() {
        Brand brand = entityManager.find(Brand.class, 10L);
        Category category = entityManager.find(Category.class, 15L);

        Product product = new Product();
        product.setName("Samsung Galaxy A31");
        product.setAlias("samsung-galaxy-a31");
        product.setShortDescription("Samsung Galaxy A31 is the best smartphone");
        product.setFullDescription("Samsung Galaxy A31 smartphone is the perfect choice if you want to " +
                "boost your smartphone sales and get more customers for your business.");
        product.setPrice(1000);
        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        product.setBrand(brand);
        product.setCategory(category);

        Product savedProduct = repo.save(product);
        assertThat(savedProduct.getId()).isNotNull();
    }

    @Test
    public void testListAllProducts() {
        repo.findAll().forEach(System.out::println);
    }

    @Test
    public void testGetById() {
        Product product = repo.findById(1L).orElse(null);
        assertThat(product).isNotNull();
    }

    @Test
    public void testUpdateProduct() {
        Product product = repo.findById(1L).orElse(null);
        assertThat(product).isNotNull();
        product.setName("Samsung Galaxy A31 Updated");
        product.setAlias("samsung-galaxy-a31-updated");

        Product updatedProduct = repo.save(product);
        assertThat(updatedProduct.getName()).isEqualTo("Samsung Galaxy A31 Updated");
    }

    @Test
    public void testDeleteProduct() {
        Product product = repo.findById(1L).orElse(null);
        assertThat(product).isNotNull();
        repo.delete(product);
        product = repo.findById(1L).orElse(null);
        assertThat(product).isNull();
    }

    @Test
    public void testCreateProductWithImages() {
        Product product = repo.getById(1L);
        assertThat(product).isNotNull();
        product.setMainImage("/images/products/1/main.jpg");
        product.addExtraImage("/images/products/1/extras/1.jpg");
        product.addExtraImage("/images/products/1/extras/2.jpg");
        repo.save(product);
        assertThat(product.getMainImage()).isEqualTo("/images/products/1/main.jpg");
    }

    @Test
    public void testCreateProductWithDetails() {
        Product product = repo.findById(2L).orElse(null);
        assertThat(product).isNotNull();
        product.addDetail("cpu", "i7");
        product.addDetail("ram", "8gb");
        product.addDetail("screen", "6.5");
        Product savedProduct = repo.save(product);
        assertThat(savedProduct.getDetails().size()).isGreaterThanOrEqualTo(3);
    }
}
