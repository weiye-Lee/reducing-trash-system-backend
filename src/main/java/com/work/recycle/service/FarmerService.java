package com.work.recycle.service;

import com.work.recycle.component.OrdersComponent;
import com.work.recycle.entity.*;
import com.work.recycle.repository.*;
import com.work.recycle.utils.SwitchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * The type Farmer service.
 */
@Service
@Slf4j
public class FarmerService {
    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private FCOrderRepository fcOrderRepository;
    @Autowired
    private BaseOrderRepository baseOrderRepository;
    @Autowired
    private OrdersComponent ordersComponent;


    private int uid = 1;

    public int getScore() {
        return farmerRepository.getScoreById(uid);
    }

    public int getOrderTimes() {
        return fcOrderRepository.getFarmerFCOrderTimesById(uid);
    }

    public void addFCOrder(BaseOrder order, List<GarbageChoose> garbageChooses) {

        ordersComponent.addOrder(order,garbageChooses, SwitchUtil.FCORDER);
    }

    public List<FCOrder> getOrders() {

        return fcOrderRepository.getFarmerFCOrdersById(uid);
    }

    public BaseOrder getOrderInfo(int id) {
        return baseOrderRepository.getBaseOrderById(id);
    }
}
