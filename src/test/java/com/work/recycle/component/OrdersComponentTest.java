package com.work.recycle.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.Farmer;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.GarbageChoose;
import com.work.recycle.repository.FarmerRepository;
import com.work.recycle.utils.SwitchUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class OrdersComponentTest {

    @Autowired
    private OrdersComponent ordersComponent;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private FarmerRepository farmerRepository;
    @Test
    void addOrder() throws JsonProcessingException {
        // 构造garbage list集合
        Garbage garbage = new Garbage();
        Garbage garbage1 = new Garbage();
        garbage.setId(1);
        garbage1.setId(2);
        GarbageChoose garbageChoose = new GarbageChoose();
        GarbageChoose garbageChoose1 = new GarbageChoose();
        garbageChoose.setGarbage(garbage);
        garbageChoose.setAmount(11);
        garbageChoose1.setGarbage(garbage1);
        garbageChoose1.setAmount(22);


        log.warn("{}", mapper.writeValueAsString(garbageChoose));
        List<GarbageChoose> list = new ArrayList<>();
        list.add(garbageChoose);
        list.add(garbageChoose1);

        log.warn("{}", mapper.writeValueAsString(list));

        // 构造baseOrder
        BaseOrder baseOrder = new BaseOrder();
        baseOrder.setRemark("能否成功添加fc订单农户");

        baseOrder.setGarbageChooses(list);

        log.warn(mapper.writeValueAsString(baseOrder));

        ordersComponent.addOrder(baseOrder,list, SwitchUtil.FCORDER);

    }


    @Test
    void test_getFarmer() throws JsonProcessingException {
        int uid = 1;
        Farmer farmer = farmerRepository.getFarmerById(uid);
        log.warn(mapper.writeValueAsString(farmer));
    }

    @Test
    void checkBaseOrderGarbage() {
        BaseOrder baseOrder = new BaseOrder();
        List<GarbageChoose> list = new ArrayList<>();
        GarbageChoose choose = new GarbageChoose();
        baseOrder.setId(1);
        Garbage g = new Garbage();
        g.setId(10);
        choose.setGarbage(g);
        choose.setId(45);
        choose.setAmount(10);
        list.add(choose);
        ordersComponent.checkBaseOrderGarbage(baseOrder,list);
    }
}