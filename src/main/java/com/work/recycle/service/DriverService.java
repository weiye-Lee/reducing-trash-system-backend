package com.work.recycle.service;

import com.work.recycle.entity.*;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.*;
import com.work.recycle.utils.CalculateScore;
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

    public void checkCDOrder(BaseOrder order, List<GarbageChoose> garbageChooses) {
        CDOrder cdOrder = cdOrderRepository.getCDOrderById(order.getId());
        int uid = 5;
        Driver driver = driverRepository.getDriverById(uid);
        cdOrder.setDriver(driver);
        int score = CalculateScore.getScore(garbageChooses);
        order.setScore(score);

        garbageChooses.forEach(each -> {
            int garbageId = each.getGarbage().getId();
            garbageRepository.findById(garbageId)
                    .ifPresentOrElse(each::setGarbage
                            ,() -> Asserts.fail("数据集错误"));
            each.setBaseOrder(order);
            chooseRepository.save(each);
        });
    }

    // 司机接收 保洁员的订单,即改变基础订单的状态值
    public void recieveCDOrder(BaseOrder baseOrder) {
        baseOrder.setReceiveStatus(true);
        baseOrderRepository.save(baseOrder);
    }
}
