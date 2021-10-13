package com.shopme.admin.user;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoderTest {
    @Test
    public void testPasswordEncoded() {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String password = "123";
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        System.out.println(encodedPassword);
        boolean match = bCryptPasswordEncoder.matches(password, encodedPassword);
        Assertions.assertThat(match).isTrue();
    }

}
