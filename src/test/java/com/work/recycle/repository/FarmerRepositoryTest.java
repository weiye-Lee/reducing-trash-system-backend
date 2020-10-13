package com.work.recycle.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.core.AutoConfigureCache;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class FarmerRepositoryTest {
    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private GarbageRepository garbageRepository;
    @Autowired
    private GarbageChooseRepository chooseRepository;

    @Test
    void test_addFarmer() {
        Farmer f1 = new Farmer();
        User u1 = new User();
        u1.setName("tom");
        u1.setPhoneNumber("13050496540");
        u1.setId(1);
        f1.setUser(u1);
        farmerRepository.save(f1);


    }

    @Test
    void test_Farmer() {
        Farmer farmer = new Farmer();
        farmer.setId(66);
        farmerRepository.save(farmer);

    }

    @Test
    void getFarmerById() throws JsonProcessingException {
        Farmer farmer = farmerRepository.getFarmerById(1);
        log.warn("{}",mapper.writeValueAsString(farmer));
        Cleaner cleaner = farmer.getCleaner();
        log.warn(cleaner.getUser().getName());
    }

    @Test
    void testGetFarmerById() {
    }
}