package com.work.recycle.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.GarbageChoose;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class BaseOrderRepositoryTest {
    @Autowired
    private BaseOrderRepository baseOrderRepository;
    @Autowired
    private ObjectMapper mapper;
    @Test
    public void test_saveOrder() throws JsonProcessingException {
        Garbage g = new Garbage();
        Garbage g1 = new Garbage();
        g.setId(1);
        g.setId(2);
        GarbageChoose garbageChoose = new GarbageChoose();
        GarbageChoose garbageChoose1 = new GarbageChoose();
        garbageChoose.setGarbage(g);
        garbageChoose1.setGarbage(g1);
        List<GarbageChoose> list = new ArrayList<>();
        list.add(garbageChoose);
        list.add(garbageChoose1);
        log.warn("{}",list);

        BaseOrder order = new BaseOrder();
        order.setGarbageChooses(list);
        order.setRemark("插入级联");
        log.warn(mapper.writeValueAsString(order));
        baseOrderRepository.save(order);
    }
}