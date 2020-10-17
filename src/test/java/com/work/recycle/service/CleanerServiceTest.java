package com.work.recycle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class CleanerServiceTest {
    @Autowired
    private CleanerService cleanerService;
    @Autowired
    private ObjectMapper mapper;

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
    void test_getOrders() {
        cleanerService.getFCOrders()
                .forEach(each -> {
                    try {
                        log.warn(each.toString());
                        log.warn(mapper.writeValueAsString(each));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                });
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
}