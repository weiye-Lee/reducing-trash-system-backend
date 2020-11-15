package com.work.recycle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.*;
import com.work.recycle.repository.CleanerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class CleanerServiceTest {
    @Autowired
    private CleanerService cleanerService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private CleanerRepository cleanerRepository;
    @Test
    void checkFCOrder() throws JsonProcessingException {

        BaseOrder baseOrder = new BaseOrder();
        baseOrder.setId(22);
        Garbage garbage = new Garbage();
        Garbage garbage1 = new Garbage();
        garbage.setId(1);
        garbage1.setId(2);

        GarbageChoose garbageChoose = new GarbageChoose();
        GarbageChoose garbageChoose1 = new GarbageChoose();
        garbageChoose.setGarbage(garbage);
        garbageChoose1.setGarbage(garbage1);
        List<GarbageChoose> list = new ArrayList<>();
        list.add(garbageChoose);
        list.add(garbageChoose1);

        log.warn(mapper.writeValueAsString(baseOrder));
        log.warn(mapper.writeValueAsString(list));
        //cleanerService.checkFCOrder(baseOrder,list);

    }


    @Test
    void test_saveAll() {

    }

    @Test
    void addCDOrder() throws JsonProcessingException {
        BaseOrder baseOrder = new BaseOrder();
        baseOrder.setId(22);
        Garbage garbage = new Garbage();
        Garbage garbage1 = new Garbage();
        garbage.setId(1);
        garbage1.setId(2);

        GarbageChoose garbageChoose = new GarbageChoose();
        GarbageChoose garbageChoose1 = new GarbageChoose();
        garbageChoose.setGarbage(garbage);
        garbageChoose.setAmount(100);

        garbageChoose1.setGarbage(garbage1);
        garbageChoose1.setAmount(500);
        List<GarbageChoose> list = new ArrayList<>();
        list.add(garbageChoose);
        list.add(garbageChoose1);

        log.warn(mapper.writeValueAsString(baseOrder));
        cleanerService.addCDOrder(baseOrder,list);
    }

    @Test
    void testCheckFCOrder() {
    }

    @Test
    void test_rankList() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("保洁员姓名");
            String phone = "1305049656" + i;
            user.setPhoneNumber(phone);
            user.setPassword(encoder.encode("123456"));
            user.setRole(User.Role.CLEANER);
            user.setVillage("水杯村");
            Cleaner cleaner = new Cleaner();
            cleaner.setUser(user);
            cleaner.setScore(i);
            cleanerRepository.save(cleaner);
        }
    }
}