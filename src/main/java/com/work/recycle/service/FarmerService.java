package com.work.recycle.service;

import com.work.recycle.common.ResultCode;
import com.work.recycle.component.ConstructVoComponent;
import com.work.recycle.component.OrdersComponent;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.controller.vo.AddressVo;
import com.work.recycle.controller.vo.IndexOrderVo;
import com.work.recycle.controller.vo.SiftOrderVo;
import com.work.recycle.entity.*;
import com.work.recycle.exception.ApiException;
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
    @Autowired
    private ConstructVoComponent constructVoComponent;

    public int getScore() {
        int uid = requestComponent.getUid();
        return farmerRepository.getScoreById(uid);
    }

    public int getOrderTimes() {
        int uid = requestComponent.getUid();
        return fcOrderRepository.getFarmerFCOrderTimesById(uid);
    }

    public void addFCOrder(BaseOrder order, List<GarbageChoose> garbageChooses) {

        ordersComponent.addOrder(order, garbageChooses, SwitchUtil.FCORDER);
    }

    /**
     * 获取审核完成订单
     *
     * @return 标准类型订单列表
     */
    public List<IndexOrderVo> getFCOrderChecked(SiftOrderVo siftOrderVo) {
        return constructVoComponent.getCommonOrders(true, SwitchUtil.FCORDER,siftOrderVo);
    }

    /**
     * 获取正在审核订单
     *
     * @return 标准类型订单列表
     */
    public List<IndexOrderVo> getFCOrderChecking(SiftOrderVo siftOrderVo) {
        return constructVoComponent.getCommonOrders(false, SwitchUtil.FCORDER,siftOrderVo);
    }


    /**
     * 农户积分排行榜
     *
     * @return the farmer list
     */
    public List<Farmer> getRankList(AddressVo addressVo) {
        // 从 村 -> 街道 -> 区 -> 市 -> 省 依次判空，如果区域级别小，则按照小的查询
        log.warn(addressVo.getVillage());
        if (addressVo == null) {
            return farmerRepository.findTop10ByOrderByScoreDesc();
        } else if (addressVo.getVillage() != null) {
            return farmerRepository.findTop10ByUser_VillageOrderByScoreDesc(addressVo.getVillage());
        } else if (addressVo.getStreet() != null) {
            return farmerRepository.findTop10ByUser_StreetOrderByScoreDesc(addressVo.getStreet());
        } else if (addressVo.getArea() != null) {
            return farmerRepository.findTop10ByUser_AreaOrderByScoreDesc(addressVo.getArea());
        } else if (addressVo.getCity() != null) {
            return farmerRepository.findTop10ByUser_CityOrderByScoreDesc(addressVo.getCity());
        } else if (addressVo.getProvince() != null) {
            return farmerRepository.findTop10ByUser_ProvinceOrderByScoreDesc(addressVo.getProvince());
        } else {
            return farmerRepository.findTop10ByOrderByScoreDesc();
        }
    }
}
