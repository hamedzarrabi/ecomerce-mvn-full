package com.shopme.admin.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;


public class PasswordEncoderTest {
    @Test
    public void testEncodePassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "lightwave";
        String encode = passwordEncoder.encode(rawPassword);

        System.out.println(encode);

        boolean matches = passwordEncoder.matches(rawPassword, encode);

        assertThat(matches).isTrue();
    }
}
