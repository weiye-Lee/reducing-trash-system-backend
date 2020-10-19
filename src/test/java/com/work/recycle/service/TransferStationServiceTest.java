package com.work.recycle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.GarbageChoose;
import com.work.recycle.repository.BaseOrderRepository;
import com.work.recycle.repository.FarmerRepository;
import com.work.recycle.repository.GarbageChooseRepository;
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
class TransferStationServiceTest {

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
    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private TransferStationService transferStationService;
    @Test
    void addDTOrder() throws JsonProcessingException {
        // 构造garbage list集合
        Garbage garbage = garbageRepository.getGarbageById(1);
        Garbage garbage1 = garbageRepository.getGarbageById(2);
        GarbageChoose garbageChoose = new GarbageChoose();
        GarbageChoose garbageChoose1 = new GarbageChoose();
        garbageChoose.setGarbage(garbage);
        garbageChoose1.setGarbage(garbage1);

        log.warn("{}", mapper.writeValueAsString(garbageChoose));
        List<GarbageChoose> list = new ArrayList<>();
        list.add(garbageChoose);
        list.add(garbageChoose1);

        log.warn("{}", mapper.writeValueAsString(list));

        // 构造baseOrder
        BaseOrder baseOrder = new BaseOrder();
        baseOrder.setRemark("明天去看升国旗");


        log.warn(mapper.writeValueAsString(baseOrder));
        transferStationService.addDTOrder(baseOrder,list,1);

    }
}