package com.work.recycle.service;

import com.work.recycle.entity.*;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.*;
import com.work.recycle.component.CalculateScore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    @Autowired
    private CalculateScore calculateScore;
    @Autowired
    private DriverRepository driverRepository;

    private int uid = 5;

    public int getFCOrderTime(int id) {
        return fcOrderRepository.getCleanerFCOrderTimesById(id);
    }

    // 审核农户订单
    public int checkFCOrder(BaseOrder order,List<GarbageChoose> garbageChooses) {
        FCOrder fcOrder = fcOrderRepository.getFCOrderById(order.getId());
        Cleaner cleaner = cleanerRepository.getCleanerById(uid);
        fcOrder.setCleaner(cleaner);
        int score = calculateScore.getScore(garbageChooses);
        order.setScore(score);
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

    public int addCDOrder(BaseOrder order,List<GarbageChoose> garbageChooses){
        CDOrder cdOrder = new CDOrder();
        Cleaner cleaner = cleanerRepository.getCleanerById(uid);
        order.setScore(calculateScore.getScore(garbageChooses));
        cdOrder.setBaseOrder(order);
        cdOrder.setCleaner(cleaner);

        cdOrderRepository.save(cdOrder);

        garbageChooses.forEach(each -> {
            int garbageId = each.getGarbage().getId();
            Optional<Garbage> garbage = garbageRepository.findById(garbageId);
            garbage.ifPresentOrElse(each::setGarbage
                    , () -> Asserts.fail("数据集错误"));
            each.setBaseOrder(order);
            chooseRepository.save(each);
        });

        return uid;

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
