package com.shopme.admin.user;

import com.shopme.admin.paging.SearchRepository;
import com.shopme.common.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends SearchRepository<User, Long> {
    User findUserByEmail(String email);

    Long countById(Long id);

    @Query("UPDATE User user SET user.enabled = ?2 where user.id = ?1")
    @Modifying
    void updateEnabledStatus(Long id, boolean enabled);

    @Query("SELECT user FROM User user WHERE CONCAT(user.id, ' ', user.email, ' ', user.firstName, ' ', user.lastName) " +
            "LIKE %?1%")
    Page<User> findAll(String keyword, Pageable pageable);
}
