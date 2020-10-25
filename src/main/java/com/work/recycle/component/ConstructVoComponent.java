package com.work.recycle.component;


import com.work.recycle.controller.vo.IndexOrderVo;
import com.work.recycle.entity.CDOrder;
import com.work.recycle.entity.CROrder;
import com.work.recycle.entity.FCOrder;
import com.work.recycle.entity.User;
import com.work.recycle.repository.CDOrderRepository;
import com.work.recycle.repository.CROrderRepository;
import com.work.recycle.repository.FCOrderRepository;
import com.work.recycle.repository.UserRepository;
import com.work.recycle.utils.SwitchUtil;
import com.work.recycle.utils.VoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
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
    @Autowired
    private UserRepository userRepository;

    public List<IndexOrderVo> getCommonOrders(boolean status,String opt) {
        List<IndexOrderVo> list = new ArrayList<>();
        int uid = requestComponent.getUid();
        switch (opt) {
            case SwitchUtil.FCORDER:
                list = getCommonFCOrders(uid,status);
                break;
            case SwitchUtil.CDORDER:
                list = getCommonCDOrders(uid,status);
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
    private List<IndexOrderVo> getCommonCDOrders(int uid,Boolean status) {
        List<CDOrder> cdOrders = cdOrderRepository.getCDOrdersByCleanerAndBaseOrder(uid, status);
        List<IndexOrderVo> indexOrderVos = new ArrayList<>();
        cdOrders.forEach(each -> {
            IndexOrderVo indexOrderVo = VoUtil.constructIndexOrder(each.getBaseOrder());
            indexOrderVos.add(indexOrderVo);
        });
        return indexOrderVos;
    }

    /**
     * 返回标准类型订单列表
     * @param status 审核状态
     * @return 标准订单类型 list
     */
    private List<IndexOrderVo> getCommonFCOrders(int uid,Boolean status) {
        User user = userRepository.getUserById(uid);
        List<FCOrder> fcOrders = new ArrayList<>();
        if (user.getRole() == User.Role.CLEANER) {
           fcOrders = fcOrderRepository.getCleanerFCOrdersById(uid,status);
        } else if(user.getRole() == User.Role.FARMER) {
           fcOrders = fcOrderRepository.getFarmerFCOrdersById(uid,status);
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
     * @param uid user id
     * @return 标准订单类型 list
     */
    private List<IndexOrderVo> getCommonCROrders(int uid) {
        List<CROrder> crOrders = crOrderRepository.getCROrdersByRecycleFirm(uid);
        List<IndexOrderVo> indexOrderVos = new ArrayList<>();
        crOrders.forEach(each -> {
            IndexOrderVo indexOrderVo = VoUtil.constructIndexOrder(each.getBaseOrder());
            indexOrderVos.add(indexOrderVo);
        });
        return indexOrderVos;
    }
}
