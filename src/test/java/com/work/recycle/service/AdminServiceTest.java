package com.work.recycle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.RecyclePrice;
import com.work.recycle.entity.User;
import com.work.recycle.repository.GarbageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class AdminServiceTest {
    @Autowired
    private AdminService adminService;
    @Autowired
    private GarbageRepository garbageRepository;
    @Autowired
    private ObjectMapper mapper;
    @Test
    void add_cleaner() {
        User user = new User();
        user.setName("test");
        user.setPhoneNumber("12050496540");
        user.setRole(User.Role.CLEANER);
        adminService.addCleaner(user);
    }

    @Test
    void add_admin() {

    }

    @Test
    void setRecyclePrice() throws JsonProcessingException {
        List<Garbage> list = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            if (garbageRepository.getGarbageById(i) == null) {
                continue;
            }
            Garbage garbage = new Garbage();
            garbage.setId(i);
            garbage.setRecyclePrice(1.0);
            garbage.setUnit("斤");
            list.add(garbage);
        }
        // log.warn(mapper.writeValueAsString(list));

        adminService.setRecyclePrice(list);
    }

}