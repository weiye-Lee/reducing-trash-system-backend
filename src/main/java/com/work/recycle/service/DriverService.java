package com.work.recycle.service;

import com.work.recycle.entity.*;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.*;
import com.work.recycle.component.CalculateScore;
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
    private CalculateScore calculateScore;

    private int uid = 6;

    public int checkCDOrder(BaseOrder order, List<GarbageChoose> garbageChooses) {
        CDOrder cdOrder = cdOrderRepository.getCDOrderById(order.getId());
        Driver driver = driverRepository.getDriverById(uid);
        cdOrder.setDriver(driver);
        order.setScore(calculateScore.getScore(garbageChooses));
        baseOrderRepository.save(order);

        garbageChooses.forEach(each -> {
            int garbageId = each.getGarbage().getId();
            garbageRepository.findById(garbageId)
                    .ifPresentOrElse(each::setGarbage
                            ,() -> Asserts.fail("数据集错误"));
            each.setBaseOrder(order);
            chooseRepository.save(each);
        });
        return uid;
    }

    // 司机接收 保洁员的订单,即改变基础订单的状态值
    public int receiveCDOrder(int id) {
        BaseOrder baseOrder = baseOrderRepository.getBaseOrderById(id);
        CDOrder cdOrder = cdOrderRepository.getCDOrderById(id);
        Driver driver = driverRepository.getDriverById(uid);
        cdOrder.setDriver(driver);
        baseOrder.setReceiveStatus(true);
        cdOrderRepository.save(cdOrder);
        return uid;
    }
}
