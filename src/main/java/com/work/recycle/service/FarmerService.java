package com.work.recycle.service;

import com.work.recycle.component.OrdersComponent;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.entity.*;
import com.work.recycle.repository.*;
import com.work.recycle.utils.SwitchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    @Autowired
    private RequestComponent requestComponent;


    public int getScore() {
        int uid = requestComponent.getUid();
        return farmerRepository.getScoreById(uid);
    }

    public int getOrderTimes() {
        int uid = requestComponent.getUid();
        return fcOrderRepository.getFarmerFCOrderTimesById(uid);
    }

        public void addFCOrder(BaseOrder order, List<GarbageChoose> garbageChooses) {

        ordersComponent.addOrder(order,garbageChooses,SwitchUtil.FCORDER);
    }


    public List<FCOrder> getOrders() {
        int uid = requestComponent.getUid();
        return fcOrderRepository.getFarmerFCOrdersById(uid);
    }

    public BaseOrder getBaseOrderById(int id) {
        return baseOrderRepository.getBaseOrderById(id);
    }
}
