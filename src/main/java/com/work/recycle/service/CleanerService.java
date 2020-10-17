package com.work.recycle.service;

import com.work.recycle.component.OrdersComponent;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.entity.*;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.*;
import com.work.recycle.utils.SwitchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Cleaner service.
 */
@Service
@Slf4j
public class CleanerService {
    @Autowired
    private FCOrderRepository fcOrderRepository;
    @Autowired
    private BaseOrderRepository baseOrderRepository;
    @Autowired
    private OrdersComponent ordersComponent;
    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private CleanerRepository cleanerRepository;

    public int getFCOrderTimes() {
        int uid = requestComponent.getUid();
        return fcOrderRepository.getCleanerFCOrderTimesById(uid);
    }

    // 审核农户订单
    public void checkFCOrder(BaseOrder order,List<GarbageChoose> garbageChooses) {
        ordersComponent.checkOrder(order,garbageChooses,SwitchUtil.FCORDER);
    }

    public BaseOrder receiveFCOrder(int id) {
        FCOrder fcOrder = fcOrderRepository.getFCOrderById(id);
        BaseOrder baseOrder = baseOrderRepository.getBaseOrderById(id);
        if (baseOrder == null) {
            Asserts.fail("数据集错误");
        }
        baseOrder.setReceiveStatus(true);
        baseOrderRepository.save(baseOrder);
        return baseOrder;

    }

    public void addCDOrder(BaseOrder order,List<GarbageChoose> garbageChooses){

        ordersComponent.addOrder(order,garbageChooses, SwitchUtil.CDORDER);

    }

    public void addCROrder(BaseOrder order,List<GarbageChoose> garbageChooses) {
        ordersComponent.addOrder(order,garbageChooses,SwitchUtil.CRORDER);

    }

    public List<FCOrder> getFCOrders() {
        int uid = requestComponent.getUid();
        log.warn("{}",uid);
        return fcOrderRepository.getCleanerFCOrdersById(uid);
    }

    public int getScore() {
        int uid = requestComponent.getUid();
        return cleanerRepository.getScoreById(uid);
    }

    public List<Cleaner> getRankList() {
        return cleanerRepository.findTop10ByOrderByScoreDesc();
    }

}
