package com.shopme.admin.user;

import com.shopme.common.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public List<User> listAll() {
        return userRepository.findAll();
    }

    public User save(User user) {
        encodePassword(user);
        return userRepository.save(user);
    }

    public void encodePassword(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(String email) {
        return userRepository.findUserByEmail(email) == null;
    }

}
