package com.work.recycle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.FCOrder;
import com.work.recycle.entity.Garbage;
import com.work.recycle.repository.GarbageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class FarmerServiceTest {

    @Autowired
    private FarmerService farmerService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper mapper;
    /*
    @Test
    void testAddFCOrder() throws JsonProcessingException {
        // Garbage g1  = userService.getGarbageById(1);
        // Garbage g2  = userService.getGarbageById(2);
        // Garbage g3  = userService.getGarbageById(3);
        // Garbage g4  = userService.getGarbageById(4);

        Garbage g = new Garbage();
        g.setName("don");
        Garbage g1 = new Garbage();
        g1.setName("don1");
        Garbage g2 = new Garbage();
        g2.setName("don2");
        Garbage g3 = new Garbage();
        g3.setName("don3");
        List<Garbage> garbageList = new ArrayList<>();
        garbageList.add(g);
        garbageList.add(g1);
        garbageList.add(g2);
        garbageList.add(g3);

        log.warn("{}",mapper.writeValueAsString(garbageList));
        FCOrder fcOrder = new FCOrder();
        fcOrder.setGarbageList(garbageList);
        farmerService.addFCOrder(fcOrder);
        log.warn("撒花");
    }

     */

    @Test
    public void test_mapper() throws JsonProcessingException {
        Garbage garbage = new Garbage();
        garbage.setName("tom");
        garbage.setAmount(10);
        log.warn("{}",garbage);
        String str = mapper.writeValueAsString(garbage);

        log.warn(str);
        Object garbage1 = mapper.readValue(str,Object.class);
        log.warn("{}",garbage1);

    }
}