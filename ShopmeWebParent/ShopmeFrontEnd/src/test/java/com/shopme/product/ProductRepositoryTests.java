package com.shopme.product;

import com.shopme.common.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTests {
    @Autowired
    private ProductRepository repo;

    @Test
    public void testFindProductByAlias() {
        String alias = "SHAWE-Phone-Holder-Bed-Gooseneck-Mount";
        Product product = repo.findByAlias(alias);
        assert product != null;
    }
}
