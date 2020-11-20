package com.work.recycle.controller;

import com.work.recycle.entity.Cleaner;
import com.work.recycle.entity.Farmer;
import com.work.recycle.entity.User;
import com.work.recycle.repository.CleanerRepository;
import com.work.recycle.repository.FarmerRepository;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest

@Slf4j
class CleanerControllerTest {

    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CleanerRepository cleanerRepository;

    @Test
    void test_addFarmer() {
        Cleaner cleaner = cleanerRepository.getCleanerById(2);
        for (int i = 0; i < 20; i++) {
            String name = "农户姓名" + i;
            String phoneNum = "130504965" + i;
            User user = new User();
            user.setName(name);
            user.setPhoneNumber(phoneNum);
            user.setPassword(encoder.encode("123456"));
            user.setRole(User.Role.FARMER);
            user.setVillage("手机村");
            Farmer farmer = new Farmer();
            farmer.setUser(user);
            farmer.setScore(i);
            farmer.setCleaner(cleaner);
            farmerRepository.save(farmer);
        }
    }

    @Test
    void getFarmer() {

    }

    @Test
    void getFarmerRanklist() {
    }

    @Test
    void removeCROrders() {
    }
}