package com.shopme.admin.user;

import com.shopme.common.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findUserByEmail(String email);

    Long countById(Long id);
}
