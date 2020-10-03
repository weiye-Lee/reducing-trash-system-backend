package com.work.recycle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.GarbageChoose;
import com.work.recycle.repository.BaseOrderRepository;
import com.work.recycle.repository.GarbageChooseRepository;
import com.work.recycle.repository.GarbageRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Slf4j
class FarmerServiceTest {

    @Autowired
    private FarmerService farmerService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private GarbageChooseRepository chooseRepository;
    @Autowired
    private BaseOrderRepository orderRepository;
    @Autowired
    private GarbageRepository garbageRepository;

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
        log.warn("{}",garbage);
        String str = mapper.writeValueAsString(garbage);

        log.warn(str);
        Object garbage1 = mapper.readValue(str,Object.class);
        log.warn("{}",garbage1);

    }

    @Test
    void addFCOrder() throws JsonProcessingException {
        BaseOrder order = new BaseOrder();
        order.setRemark("nothing to say");
        order.setAddress("火星");
        Garbage g = new Garbage();
        Garbage g2 = new Garbage();
        g.setId(1);
        g2.setId(2);

        GarbageChoose garbageChoose = new GarbageChoose();
        garbageChoose.setGarbage(g);
        GarbageChoose garbageChoose1 = new GarbageChoose();
        garbageChoose1.setGarbage(g2);
        List<GarbageChoose> list = new ArrayList<>();
        list.add(garbageChoose);
        list.add(garbageChoose1);

        farmerService.addFCOrder(order,list);


    }
    @Test
    void fuck_code() throws JsonProcessingException {
        Optional<GarbageChoose> garbageChoose = chooseRepository.findById(1);
        GarbageChoose g = garbageChoose.get();
        log.warn("{}",mapper.writeValueAsString(g));
        BaseOrder baseOrder = orderRepository.getBaseOrderById(1);
        log.warn("{}",mapper.writeValueAsString(baseOrder));
        g.setBaseOrder(baseOrder);
        chooseRepository.save(g);
        log.warn("fuck the code");

    }

    @Test
    void test_addFCOrder() throws JsonProcessingException {
        // 构造garbage list集合
        Garbage garbage = garbageRepository.getGarbageById(1);
        Garbage garbage1 = garbageRepository.getGarbageById(2);
        GarbageChoose garbageChoose = new GarbageChoose();
        GarbageChoose garbageChoose1 = new GarbageChoose();
        garbageChoose.setGarbage(garbage);
        garbageChoose1.setGarbage(garbage1);

        log.warn("{}",mapper.writeValueAsString(garbageChoose));
        List<GarbageChoose> list = new ArrayList<>();
        list.add(garbageChoose);
        list.add(garbageChoose1);

        log.warn("{}",mapper.writeValueAsString(list));

        // 构造baseOrder
        BaseOrder baseOrder = new BaseOrder();
        baseOrder.setRemark("明天去看升国旗");

        baseOrder.setGarbageChooses(list);

        log.warn(mapper.writeValueAsString(baseOrder));

        // farmerService.addFCOrder(baseOrder,list);


    }
}