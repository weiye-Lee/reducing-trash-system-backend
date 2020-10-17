package com.work.recycle.service;

import com.work.recycle.component.OrdersComponent;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.controller.vo.IndexOrderVo;
import com.work.recycle.entity.*;
import com.work.recycle.repository.*;
import com.work.recycle.utils.SwitchUtil;
import com.work.recycle.utils.VoUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    /**
     * 返回标准类型订单列表
     * @param status 审核状态
     * @return 标准订单类型 list
     */
    private List<IndexOrderVo> getCommonOrders(Boolean status) {
        int uid = requestComponent.getUid();
        List<FCOrder> fcOrders = fcOrderRepository.getFarmerFCOrdersById(uid,status);
        List<IndexOrderVo> indexOrderVos = new ArrayList<>();
        fcOrders.forEach(each -> {
            IndexOrderVo indexOrderVo = VoUtil.constructIndexOrder(each.getBaseOrder());
            indexOrderVos.add(indexOrderVo);
        });
        return indexOrderVos;
    }

    /**
     * 获取审核完成订单
     * @return  标准类型订单列表
     */
    public List<IndexOrderVo> getFCOrderChecked() {
        return getCommonOrders(true);
    }

    /**
     * 获取正在审核订单
     * @return 标准类型订单列表
     */
    public List<IndexOrderVo> getFCOrderChecking() {
        return getCommonOrders(false);
    }

    /**
     * 通过id获取订单详情
     * @param id 基础订单id
     * @return baseOrder
     */
    public BaseOrder getBaseOrderById(int id) {
        return baseOrderRepository.getBaseOrderById(id);
    }

    public List<Farmer> getRankList() {
        return farmerRepository.findTop10ByOrderByScoreDesc();
    }
}
