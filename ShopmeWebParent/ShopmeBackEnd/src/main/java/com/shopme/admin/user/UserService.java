package com.shopme.admin.user;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private static final int PER_PAGE = 4;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository repo;

    public List<User> listAll() {
        return repo.findAll(Sort.by("firstName").ascending());
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(repo, pageNum, PER_PAGE);
    }

    public User save(User user) {
        boolean isUpdatingUser = user.getId() != null;
        if (isUpdatingUser) {
            User existingUser = repo.getById(user.getId());
            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }
        return repo.save(user);
    }

    public void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Long id, String email) {
        User user = repo.findUserByEmail(email);
        if (user == null) {
            return true;
        } else return Objects.equals(user.getId(), id);
    }

    public User findById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Could not found user with ID " + id));
    }

    public void delete(Long id) {
        Long countById = repo.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not found user with ID " + id);
        }
        repo.deleteById(id);
    }

    public void updateUserEnabledStatus(Long id, boolean status) {
        repo.updateEnabledStatus(id, status);
    }

    public User findByEmail(String email) {
        return repo.findUserByEmail(email);
    }

    public User updateAccount(User account) {
        Optional<User> opt = repo.findById(account.getId());
        User user = opt.orElseThrow(() -> new UserNotFoundException("Not found User"));
        if (!account.getPassword().isEmpty()) {
            user.setPassword(account.getPassword());
            encodePassword(user);
        }
        if (account.getPhotos() != null) {
            user.setPhotos(account.getPhotos());
        }

        user.setFirstName(account.getFirstName());
        user.setLastName(account.getLastName());
        return repo.save(user);
    }

}
