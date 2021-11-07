package com.shopme.admin.user;

import com.shopme.common.entity.PageInfo;
import com.shopme.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.shopme.common.utils.Common.setPageInfo;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public static final int USERS_PER_PAGE = 4;

    public List<User> listAll() {
        return userRepository.findAll(Sort.by("firstName").ascending());
    }

    public List<User> listByPage(PageInfo pageInfo, int pageNum, String sortField, String sortType, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortType.equals("asc") ? sort.ascending() : sort.descending();

        PageRequest pageable = PageRequest.of(pageNum - 1, USERS_PER_PAGE, sort);

        Page<User> userPage;
        if (keyword != null) {
            userPage = userRepository.findAll(keyword, pageable);
        } else {
            userPage = userRepository.findAll(pageable);
        }

        long startCount = (long) (pageNum - 1) * USERS_PER_PAGE + 1;
        long endCount = startCount + USERS_PER_PAGE - 1;
        if (endCount > userPage.getTotalElements()) {
            endCount = userPage.getTotalElements();
        }
        pageInfo.setStartCount(startCount);
        pageInfo.setEndCount(endCount);
        pageInfo.setCurrentPage(pageNum);
        pageInfo.setTotalPages(userPage.getTotalPages());
        pageInfo.setTotalItems(userPage.getTotalElements());

        setPageInfo(pageInfo, pageNum, userPage, USERS_PER_PAGE);

        return userPage.getContent();
    }


    public User save(User user) {
        boolean isUpdatingUser = user.getId() != null;
        if (isUpdatingUser) {
            User existingUser = userRepository.getById(user.getId());
            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }
        return userRepository.save(user);
    }

    public void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Long id, String email) {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            return true;
        } else return Objects.equals(user.getId(), id);
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("Could not found user with ID " + id));
    }

    public void delete(Long id) {
        Long countById = userRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Could not found user with ID " + id);
        }
        userRepository.deleteById(id);
    }

    public void updateUserEnabledStatus(Long id, boolean status) {
        userRepository.updateEnabledStatus(id, status);
    }

    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User updateAccount(User account) {
        Optional<User> opt = userRepository.findById(account.getId());
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
        return userRepository.save(user);
    }

}
