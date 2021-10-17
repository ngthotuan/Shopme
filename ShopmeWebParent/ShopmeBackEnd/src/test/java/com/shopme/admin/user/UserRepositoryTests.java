package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 1L);
        User nttuan = new User("nttuan@gmail.com", "123", "Tuan", "Nguyen Tho");
        nttuan.addRole(roleAdmin);
        User savedUser = userRepository.save(nttuan);
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);

    }
    @Test
    public void testCreateUserWithTwoRole() {
        Role roleEditor = new Role(3L);
        Role roleAssistant = new Role(5L);
        User cxtuan = new User("cxtuan@gmail.com", "123", "Tuan", "Chau Xuan");
        cxtuan.addRole(roleEditor);
        cxtuan.addRole(roleAssistant);
        User savedUser = userRepository.save(cxtuan);
        Assertions.assertThat(savedUser.getId()).isGreaterThan(0);

    }

    @Test
    public void testListAllUsers(){
        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);
    }

    @Test
    public void getUserById() {
        User userTuan = userRepository.getById(1L);
        System.out.println(userTuan);
        Assertions.assertThat(userTuan).isNotNull();
    }

    @Test
    public void testUpdateUser() {
        User userTuan = userRepository.getById(1L);
        userTuan.setEnabled(true);
        User userSaved = userRepository.save(userTuan);
        Assertions.assertThat(userSaved.isEnabled()).isTrue();
    }

    @Test
    public void updateUserRole() {
        User cxtuan = userRepository.getById(2L);
        cxtuan.getRoles().remove(new Role(3L));
        cxtuan.addRole(new Role(2L));

        User userSaved = userRepository.save(cxtuan);
        Assertions.assertThat(userSaved.getRoles())
                .contains(new Role(2L))
                .doesNotContain(new Role(3L));
    }

    @Test
    public void testDeleteUser() {
        Long userId = 2L;
        userRepository.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail() {
        String email = "fakeemail@gmail.com";
        User user = userRepository.findUserByEmail(email);
        Assertions.assertThat(user).isNull();
        email = "nttuan@gmail.com";
        user = userRepository.findUserByEmail(email);
        Assertions.assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {
        Long id = 1L;
        Long countById = userRepository.countById(id);
        Assertions.assertThat(countById).isGreaterThan(0);
    }

    @Test
    public void testDisabledUser() {
        Long id = 2L;
        userRepository.updateEnabledStatus(id, false);
    }

    @Test
    public void testEnabledUser() {
        Long id = 2L;
        userRepository.updateEnabledStatus(id, true);
    }

}
