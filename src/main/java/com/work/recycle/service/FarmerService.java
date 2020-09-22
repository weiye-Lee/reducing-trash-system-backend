package com.work.recycle.service;

import com.work.recycle.entity.FCOrder;
import com.work.recycle.entity.Garbage;
import com.work.recycle.repository.FCOrderRepository;
import com.work.recycle.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FarmerService {
    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private FCOrderRepository fcOrderRepository;

    public int getScore(int id) {
        return farmerRepository.getScoreById(id);
    }

    public int getOrderTimes(int id) {
        return fcOrderRepository.getFarmerFCOrderTimesById(id);
    }

    public FCOrder addFCOrder(FCOrder fcOrder) {
       fcOrderRepository.save(fcOrder);
       return null;
    }


    public List<FCOrder> getOrders(int id) {
        return fcOrderRepository.getFarmerFCOrdersById(id);
    }
}
