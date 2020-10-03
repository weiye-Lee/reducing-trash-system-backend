package com.work.recycle.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.work.recycle.entity.*;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.*;
import com.work.recycle.utils.CalculateScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Cleaner service.
 */
@Service
public class CleanerService {
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private FCOrderRepository fcOrderRepository;
    @Autowired
    private CDOrderRepository cdOrderRepository;
    @Autowired
    private GarbageRepository garbageRepository;
    @Autowired
    private GarbageChooseRepository chooseRepository;
    @Autowired
    private CROrderRepository crOrderRepository;
    @Autowired
    private BaseOrderRepository baseOrderRepository;

    private int uid = 5;

    public int getFCOrderTime(int id) {
        return fcOrderRepository.getCleanerFCOrderTimesById(id);
    }

    // 审核农户订单
    public int checkFCOrder(BaseOrder order,List<GarbageChoose> garbageChooses) {
        FCOrder fcOrder = fcOrderRepository.getFCOrderById(order.getId());
        Cleaner cleaner = cleanerRepository.getCleanerById(uid);
        fcOrder.setCleaner(cleaner);
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

        return uid;
    }

    public BaseOrder receiveFCOrder(int id) {
        FCOrder fcOrder = fcOrderRepository.getFCOrderById(id);
        BaseOrder baseOrder = baseOrderRepository.getBaseOrderById(id);
        if (baseOrder == null) {
            Asserts.fail("数据集错误");
        }
        baseOrder.setReceiveStatus(true);
        baseOrderRepository.save(baseOrder);
        return baseOrder;

    }

    public void addCDOrder(BaseOrder order,List<GarbageChoose> garbageChooses){
        CDOrder cdOrder = new CDOrder();
        Cleaner cleaner = cleanerRepository.getCleanerById(uid);
        cdOrder.setBaseOrder(order);
        cdOrder.setCleaner(cleaner);

        garbageChooses.forEach(each -> {
            int garbageId = each.getGarbage().getId();
            garbageRepository.findById(garbageId)
                    .ifPresentOrElse(each::setGarbage
                            ,()-> Asserts.fail("数据集错误"));
            chooseRepository.save(each);
        });
        cdOrderRepository.save(cdOrder);
        garbageChooses.forEach(each -> {
            each.setBaseOrder(order);
            chooseRepository.save(each);
        });

    }

    public void addCROrder(BaseOrder order,List<GarbageChoose> garbageChooses) {
        CROrder crOrder = new CROrder();
        int uid = 5;
        Cleaner cleaner = cleanerRepository.getCleanerById(uid);
        crOrder.setBaseOrder(order);
        crOrder.setCleaner(cleaner);

        garbageChooses.forEach(each -> {
            int garbageId = each.getGarbage().getId();
            garbageRepository.findById(garbageId)
                    .ifPresentOrElse(each::setGarbage
                            ,()-> Asserts.fail("数据集错误"));
            chooseRepository.save(each);
        });
        crOrderRepository.save(crOrder);
        garbageChooses.forEach(each -> {
            each.setBaseOrder(order);
            chooseRepository.save(each);
        });
    }

    public List<FCOrder> getFCOrders() {
        return fcOrderRepository.getCleanerFCOrdersById(uid);
    }

}
