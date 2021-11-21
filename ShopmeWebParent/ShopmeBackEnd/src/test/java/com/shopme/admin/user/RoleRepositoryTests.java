package com.shopme.admin.user;

import com.shopme.admin.user.role.RoleRepository;
import com.shopme.common.entity.Role;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class RoleRepositoryTests {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void testCreateFirstRole() {
        Role roleAdmin = new Role("Admin", "Manager every thing");
        Role roleSave = roleRepository.save(roleAdmin);
        Assertions.assertThat(roleSave.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateMultipleRoles() {
        Role roleSalesPerson = new Role("Salesperson", "manager product prices, " +
                "customers, shipping, orders and sales reports");
        Role roleEditor = new Role("Editor", "manager categories, brands, " +
                "products, acticles and menus");
        Role roleShipper = new Role("Shipper", "view products, view orders, " +
                "and update order status");
        Role roleAssistant = new Role("Assistant", "manager questions and reviews");

        roleRepository.saveAll(List.of(roleSalesPerson, roleEditor, roleShipper, roleAssistant));
    }
}
