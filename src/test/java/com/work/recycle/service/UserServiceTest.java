package com.work.recycle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper mapper;
    @Test
    void getGarbage() {
        userService.getGarbage()
                .forEach(a -> {
                    try {
                        log.warn("{}",mapper.writeValueAsString(a));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
    }
}