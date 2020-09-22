package com.work.recycle.service;

import com.work.recycle.entity.Cleaner;
import com.work.recycle.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class CleanerServiceTest {
    @Autowired
    private CleanerService cleanerService;

    @Test
    void addCleaner() {
        Cleaner cleaner = new Cleaner();
        User u = new User();
        u.setName("tom");
        u.setPhoneNumber("13050496540");
        cleaner.setUser(u);
        cleanerService.addCleaner(cleaner);
    }
}