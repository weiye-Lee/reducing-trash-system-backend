package com.work.recycle.service;

import com.work.recycle.common.ResultCode;
import com.work.recycle.component.ConstructVoComponent;
import com.work.recycle.component.OrdersComponent;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.controller.vo.IndexOrderVo;
import com.work.recycle.controller.vo.SiftOrderVo;
import com.work.recycle.entity.*;
import com.work.recycle.exception.ApiException;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.*;
import com.work.recycle.utils.SwitchUtil;
import com.work.recycle.utils.VoUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class RecycleFirmService {

    @Autowired
    private OrdersComponent ordersComponent;
    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private RecycleFirmRepository recycleFirmRepository;
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private CROrderRepository crOrderRepository;
    @Autowired
    private BaseOrderRepository baseOrderRepository;
    @Autowired
    private ConstructVoComponent constructVoComponent;
    @Autowired
    private UserRepository userRepository;

    public void addCROrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses,int id) {
        CROrder crOrder = new CROrder();
        int uid = requestComponent.getUid();
        RecycleFirm recycleFirm = recycleFirmRepository.getRecycleFirmById(uid);
        Cleaner cleaner = cleanerRepository.getCleanerById(id);
        if (cleaner == null) {
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }
        double score = ordersComponent.getScore(garbageChooses);
        log.warn("{}",score);
        cleaner.addScore(score);
        baseOrder.setScore(score);
        crOrder.setCleaner(cleaner);
        crOrder.setRecycleFirm(recycleFirm);
        crOrder.setBaseOrder(baseOrder);
        crOrder.setTradePrice(ordersComponent.getCRPrice(garbageChooses));
        crOrderRepository.save(crOrder);
        ordersComponent.addBaseOrderGarbageList(baseOrder,garbageChooses);
    }

    public List<IndexOrderVo> getCROrders(SiftOrderVo siftOrderVo) {
        return constructVoComponent.getCommonOrders(false,SwitchUtil.CRORDER,siftOrderVo);
    }


    public String getCleanerNameById(int id) {

        User user = userRepository.getUserById(id);
        if (user.getRole() != User.Role.CLEANER) {
            throw new ApiException(ResultCode.FORBIDDEN);
        }

        return user.getName();
    }

    public List<CROrder> getCROrders() {
        int uid = requestComponent.getUid();
        return crOrderRepository.getCROrdersByRecycleFirm(uid);
    }

    public int checkCROrder(BaseOrder baseOrder,List<GarbageChoose> garbageChooses) {
        ordersComponent.checkOrder(baseOrder,garbageChooses,SwitchUtil.CRORDER);
        return requestComponent.getUid();
    }
}
