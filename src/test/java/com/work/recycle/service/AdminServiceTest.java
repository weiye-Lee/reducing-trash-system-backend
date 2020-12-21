package com.work.recycle.service;

import com.work.recycle.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class AdminServiceTest {
    @Autowired
    private AdminService adminService;

    @Test
    void add_cleaner() {
        User user = new User();
        user.setName("test");
        user.setPhoneNumber("12050496540");
        user.setRole(User.Role.CLEANER);
        adminService.addCleaner(user);
    }

}