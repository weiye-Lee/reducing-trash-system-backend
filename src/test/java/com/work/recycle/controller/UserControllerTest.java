package com.work.recycle.controller;

import com.work.recycle.entity.User;
import com.work.recycle.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Slf4j
class UserControllerTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Test
    void getGarbage() {
        User user = new User();
        user.setRole(User.Role.ADMIN);
        user.setPhoneNumber("13050496640");
        user.setPassword(encoder.encode("123456"));
        userRepository.save(user);
    }

}