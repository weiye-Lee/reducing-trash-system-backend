package com.work.recycle.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper mapper;


    @Test
    void test_getUser() throws JsonProcessingException {
        User user= userRepository.getUserById(1);
        log.warn("{}",mapper.writeValueAsString(user));
    }

    @Test
    void test_addUser() {
        User user = new User();
        user.setName("dog");
        user.setId(66);
        user.setPhoneNumber("110");
        userRepository.save(user);
    }

    @Test
    void getUserByPhoneNumber() throws JsonProcessingException {
        User user = userRepository.getUserByPhoneNumber("13050496540");
        log.warn(mapper.writeValueAsString(user));
    }

    @Test
    void findByRole() {
        userRepository.findByRole(User.Role.FARMER)
                .forEach(user -> log.warn(user.getName()));
    }
}