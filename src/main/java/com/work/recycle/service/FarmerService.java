package com.work.recycle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.common.ResultCode;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.entity.*;
import com.work.recycle.repository.*;
import com.work.recycle.utils.CalculateScore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

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
    private RequestComponent requestComponent;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BaseOrderRepository baseOrderRepository;
    @Autowired
    private GarbageRepository garbageRepository;
    @Autowired
    private GarbageChooseRepository chooseRepository;
    @Autowired
    private ObjectMapper mapper;

    private int uid = 1;

    public int getScore() {
        return farmerRepository.getScoreById(uid);
    }

    public int getOrderTimes() {
        return fcOrderRepository.getFarmerFCOrderTimesById(uid);
    }


    /**
     * 添加一条农户预约记录
     *
     * @param order          基础订单信息
     * @param garbageChooses 对应每条每条订单记录的
     * @return the fc order
     */

    public void addFCOrder(BaseOrder order, List<GarbageChoose> garbageChooses) throws JsonProcessingException {
       FCOrder fcOrder = new FCOrder();

       // int uid = requestComponent.getUid();
       int uid = 1;
       Farmer farmer = farmerRepository.getFarmerById(uid);
       order.setScore(CalculateScore.getScore(garbageChooses));
       fcOrder.setBaseOrder(order);
       fcOrder.setFarmer(farmer);
       fcOrderRepository.save(fcOrder);
       // 遍历垃圾选择集合，作用是将垃圾 和垃圾选择建立联系
       garbageChooses.forEach(each -> {
           int garbageId = each.getGarbage().getId();
           Optional<Garbage> garbage = garbageRepository.findById(garbageId);
           garbage.ifPresent(each::setGarbage);
           chooseRepository.save(each);
       });

    }

    public List<FCOrder> getOrders() {

        return fcOrderRepository.getFarmerFCOrdersById(uid);
    }

    public BaseOrder getOrderInfo(int id) {
        return baseOrderRepository.getBaseOrderById(id);
    }
}
