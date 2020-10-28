package com.work.recycle.component;


import com.work.recycle.common.ResultCode;
import com.work.recycle.controller.vo.IndexOrderVo;
import com.work.recycle.entity.CDOrder;
import com.work.recycle.entity.CROrder;
import com.work.recycle.entity.FCOrder;
import com.work.recycle.entity.User;
import com.work.recycle.exception.ApiException;
import com.work.recycle.repository.CDOrderRepository;
import com.work.recycle.repository.CROrderRepository;
import com.work.recycle.repository.FCOrderRepository;
import com.work.recycle.utils.SwitchUtil;
import com.work.recycle.utils.VoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取 构造标准订单列表组件
 */
@Component
public class ConstructVoComponent {
    @Autowired
    private FCOrderRepository fcOrderRepository;
    @Autowired
    private CDOrderRepository cdOrderRepository;
    @Autowired
    private CROrderRepository crOrderRepository;
    @Autowired
    private RequestComponent requestComponent;

    public List<IndexOrderVo> getCommonOrders(boolean status, String opt) {
        List<IndexOrderVo> list = new ArrayList<>();
        int uid = requestComponent.getUid();
        switch (opt) {
            case SwitchUtil.FCORDER:
                list = getCommonFCOrders(uid, status);
                break;
            case SwitchUtil.CDORDER:
                list = getCommonCDOrders(uid, status);
                break;
            case SwitchUtil.CRORDER:
                list = getCommonCROrders(uid);
                break;
        }
        return list;
    }

    /**
     * 返回标准类型订单列表
     *
     * @param status 审核状态
     * @return 标准订单类型 list
     */
    private List<IndexOrderVo> getCommonCDOrders(int uid, Boolean status) {
        User.Role role = requestComponent.getRole();
        List<CDOrder> cdOrders;
        if (role == User.Role.CLEANER) {
            cdOrders = cdOrderRepository.getCDOrdersByCleanerAndBaseOrder(uid, status);
        } else if (role == User.Role.DRIVER) {
            cdOrders = cdOrderRepository.getCDOrdersByDriverAndBaseOrder(uid, status);
        } else {
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }

        List<IndexOrderVo> indexOrderVos = new ArrayList<>();
        cdOrders.forEach(each -> {
            IndexOrderVo indexOrderVo = VoUtil.constructIndexOrder(each.getBaseOrder());
            indexOrderVos.add(indexOrderVo);
        });
        return indexOrderVos;
    }

    /**
     * 构造标准fc订单列表
     *
     * @param uid    user id Notnull
     * @param status 订单审核状态
     * @return 标准订单列表集合
     */
    private List<IndexOrderVo> getCommonFCOrders(int uid, Boolean status) {
        User.Role role = requestComponent.getRole();
        List<FCOrder> fcOrders;
        if (role == User.Role.CLEANER) {
            fcOrders = fcOrderRepository.getCleanerFCOrdersById(uid, status);
        } else if (role == User.Role.FARMER) {
            fcOrders = fcOrderRepository.getFarmerFCOrdersById(uid, status);
        } else {
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }
        List<IndexOrderVo> indexOrderVos = new ArrayList<>();
        fcOrders.forEach(each -> {
            IndexOrderVo indexOrderVo = VoUtil.constructIndexOrder(each.getBaseOrder());
            indexOrderVos.add(indexOrderVo);
        });
        return indexOrderVos;
    }

    /**
     * 返回标准类型订单列表
     *
     * @param uid user id
     * @return 标准订单类型 list
     */
    private List<IndexOrderVo> getCommonCROrders(int uid) {
        User.Role role = requestComponent.getRole();
        List<CROrder> crOrders;

        if (role == User.Role.CLEANER) {
            crOrders = crOrderRepository.getCROrdersByCleaner(uid);
        } else if (role == User.Role.RECYCLEFIRM) {
            crOrders = crOrderRepository.getCROrdersByRecycleFirm(uid);
        } else {
            throw new ApiException(ResultCode.VALIDATE_FAILED);
        }

        List<IndexOrderVo> indexOrderVos = new ArrayList<>();
        crOrders.forEach(each -> {
            IndexOrderVo indexOrderVo = VoUtil.constructIndexOrder(each.getBaseOrder());
            indexOrderVos.add(indexOrderVo);
        });
        return indexOrderVos;
    }
}
