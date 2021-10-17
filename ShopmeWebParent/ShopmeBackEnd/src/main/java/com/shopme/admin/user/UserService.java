package com.shopme.admin.user;

import com.shopme.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return userRepository.findAll();
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

}
