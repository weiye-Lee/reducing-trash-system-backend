package com.work.recycle.service;

import com.work.recycle.common.ResultCode;
import com.work.recycle.component.OrdersComponent;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.controller.vo.IndexOrderVo;
import com.work.recycle.entity.*;
import com.work.recycle.exception.ApiException;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.*;
import com.work.recycle.utils.SwitchUtil;
import com.work.recycle.utils.VoUtil;
import lombok.extern.slf4j.Slf4j;
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
        crOrderRepository.save(crOrder);
        ordersComponent.addBaseOrderGarbageList(baseOrder,garbageChooses);
    }


    /**
     * 返回标准类型订单列表
     * @param status 审核状态
     * @return 标准订单类型 list
     */
    private List<IndexOrderVo> getCommonOrders() {
        int uid = requestComponent.getUid();
        List<CROrder> crOrders = crOrderRepository.getCROrdersByRecycleFirm(uid);
        List<IndexOrderVo> indexOrderVos = new ArrayList<>();
        crOrders.forEach(each -> {
            IndexOrderVo indexOrderVo = VoUtil.constructIndexOrder(each.getBaseOrder());
            indexOrderVos.add(indexOrderVo);
        });
        return indexOrderVos;
    }


    public List<IndexOrderVo> getCROrders() {
        return getCommonOrders();
    }

    public BaseOrder getBaseOrderById(int id) {
        return baseOrderRepository.getBaseOrderById(id);
    }
}
