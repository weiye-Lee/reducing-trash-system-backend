package com.work.recycle.service;

import com.work.recycle.component.OrdersComponent;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.DTOrder;
import com.work.recycle.entity.Driver;
import com.work.recycle.entity.GarbageChoose;
import com.work.recycle.utils.SwitchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferStationService {
    @Autowired
    private OrdersComponent ordersComponent;

    // 中转站添加 司机-中转站订单
    public void addDTOrder(BaseOrder baseOrder, List<GarbageChoose> garbageChooses) {
        ordersComponent.addOrder(baseOrder,garbageChooses, SwitchUtil.DTORDER);
    }
}
