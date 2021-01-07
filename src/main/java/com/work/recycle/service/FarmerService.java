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

import java.util.*;

import static java.util.Optional.ofNullable;

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
    @Autowired
    private GarbageChooseRepository chooseRepository;

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
        return constructVoComponent.getCommonOrders(true, SwitchUtil.FCORDER, siftOrderVo);
    }

    /**
     * 获取正在审核订单
     *
     * @return 标准类型订单列表
     */
    public List<IndexOrderVo> getFCOrderChecking(SiftOrderVo siftOrderVo) {
        return constructVoComponent.getCommonOrders(false, SwitchUtil.FCORDER, siftOrderVo);
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

    public Map getCleaner() {
        int uid = requestComponent.getUid();
        Cleaner cleaner = farmerRepository.getCleaner(uid);
        Map map = new HashMap();
        if (cleaner == null || cleaner.getUser() == null) {
            throw new ApiException(ResultCode.FAILED);
        }
        map.put("name", cleaner.getUser().getName());
        map.put("id",cleaner.getId());
        map.put("province", cleaner.getUser().getProvince());
        map.put("city", cleaner.getUser().getCity());
        map.put("area", cleaner.getUser().getArea());
        map.put("street", cleaner.getUser().getStreet());
        map.put("village", cleaner.getUser().getVillage());
        map.put("phoneNumber",cleaner.getUser().getPhoneNumber());

        return map;
    }

    // TODO 2020:11/20 代码重构
    public FCOrder removeFCOrder(int id) {
        FCOrder fcOrder = fcOrderRepository.getFCOrderById(id);
        if (fcOrder == null || fcOrder.getBaseOrder() == null) {
            throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
        } else if (fcOrder.getBaseOrder().getCheckStatus()) {
            throw new ApiException("审核完成订单不允许删除");
        }
        fcOrderRepository.delete(fcOrder);
        chooseRepository.getGarbageChooseByBaseOrder_Id(id)
                .forEach(garbageChoose -> chooseRepository.delete(garbageChoose));

        baseOrderRepository.deleteById(id);


        return fcOrder;
    }

}
