package com.work.recycle.repository;

import com.work.recycle.entity.*;
import com.work.recycle.service.DriverService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class DTOrderRepositoryTest {
    @Autowired
    private DTOrderRepository dtOrderRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private FCOrderRepository fcOrderRepository;
    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private GarbageRepository garbageRepository;
    @Test
    void test_save() {
        /*
        // 构造garbage list集合
        Garbage garbage = garbageRepository.getGarbageById(1);
        Garbage garbage1 = garbageRepository.getGarbageById(2);
        GarbageChoose garbageChoose = new GarbageChoose();
        GarbageChoose garbageChoose1 = new GarbageChoose();
        garbageChoose.setGarbage(garbage);
        garbageChoose1.setGarbage(garbage1);

        List<GarbageChoose> list = new ArrayList<>();
        list.add(garbageChoose);
        list.add(garbageChoose1);


        // 构造baseOrder
        BaseOrder baseOrder = new BaseOrder();
        baseOrder.setRemark("明天去看升国旗");

        baseOrder.setGarbageChooses(list);

         */

        BaseOrder baseOrder = new BaseOrder();
        Driver driver = driverRepository.getDriverById(2);
        DTOrder dtOrder = new DTOrder();
        dtOrder.setBaseOrder(baseOrder);
        dtOrder.setDriver(driver);
        dtOrderRepository.save(dtOrder);
    }

    @Test
    void test_saveFarmer() {
        Farmer farmer = farmerRepository.getFarmerById(3);
        FCOrder fcOrder = new FCOrder();
        fcOrder.setFarmer(farmer);
        fcOrderRepository.save(fcOrder);
    }
}