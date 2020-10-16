package com.work.recycle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.controller.GarbageVo;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

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
    }

    @Test
    void getUserByPhone() throws JsonProcessingException {
        User u = userService.getUserByPhone("13050496540");
        log.warn(mapper.writeValueAsString(u));
    }
}