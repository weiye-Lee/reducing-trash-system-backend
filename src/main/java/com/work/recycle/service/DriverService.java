package com.work.recycle.service;

import com.work.recycle.component.OrdersComponent;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.entity.*;
import com.work.recycle.repository.*;
import com.work.recycle.utils.SwitchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {
    @Autowired
    private BaseOrderRepository baseOrderRepository;
    @Autowired
    private GarbageRepository garbageRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private GarbageChooseRepository chooseRepository;
    @Autowired
    private CDOrderRepository cdOrderRepository;
    @Autowired
    private OrdersComponent ordersComponent;
    @Autowired
    private RequestComponent requestComponent;


    public void checkCDOrder(BaseOrder order, List<GarbageChoose> garbageChooses) {
        ordersComponent.checkOrder(order,garbageChooses, SwitchUtil.CDORDER);
    }

    // 司机接收 保洁员的订单,即改变基础订单的状态值
    public int receiveCDOrder(int id) {
        int uid = requestComponent.getUid();
        BaseOrder baseOrder = baseOrderRepository.getBaseOrderById(id);
        CDOrder cdOrder = cdOrderRepository.getCDOrderById(id);
        Driver driver = driverRepository.getDriverById(uid);
        cdOrder.setDriver(driver);
        baseOrder.setReceiveStatus(true);
        cdOrderRepository.save(cdOrder);
        return uid;
    }
}
