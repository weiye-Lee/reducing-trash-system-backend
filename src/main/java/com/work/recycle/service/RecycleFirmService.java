package com.work.recycle.service;

import com.work.recycle.component.OrdersComponent;
import com.work.recycle.entity.*;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.*;
import com.work.recycle.utils.SwitchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecycleFirmService {

    @Autowired
    private OrdersComponent ordersComponent;

    public void addCROrder(BaseOrder order, List<GarbageChoose> garbageChooses) {
        ordersComponent.addOrder(order,garbageChooses, SwitchUtil.CRORDER);
    }
}
