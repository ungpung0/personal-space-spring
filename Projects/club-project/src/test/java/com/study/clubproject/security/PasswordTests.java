package com.study.clubproject.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class PasswordTests {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void testEncode() {
        String password = "1111";
        String password_encrypt = passwordEncoder.encode(password);
        System.out.println("Encrypted Password : " + password_encrypt);
        boolean isMatch = passwordEncoder.matches(password, password_encrypt);
        System.out.println("Match Result : " + isMatch);
    }
}
