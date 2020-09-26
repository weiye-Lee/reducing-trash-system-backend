package com.work.recycle.service;

import com.work.recycle.entity.*;
import com.work.recycle.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public int getFCOrderTime(int id) {
        return fcOrderRepository.getCleanerFCOrderTimesById(id);
    }

    public List<FCOrder> getFCOrdersById(int id) {
        return null;
    }

    public void addCleaner(Cleaner cleaner) {

        cleanerRepository.save(cleaner);
    }


    // 审核农户订单
    public void checkFCOrder(FCOrder fcOrder) {

    }

    public void recieveFCOrder(FCOrder fcOrder) {}

    public void addCDOrder(BaseOrder order,List<GarbageChoose> garbageChooses){
        CDOrder cdOrder = new CDOrder();
        int uid = 1;
        Cleaner cleaner = cleanerRepository.getCleanerById(uid);
        cdOrder.setBaseOrder(order);
        cdOrder.setCleaner(cleaner);

        garbageChooses.forEach(each -> {
            int garbageId = each.getGarbage().getId();
            Garbage garbage = garbageRepository.findById(garbageId).get();
            each.setGarbage(garbage);
            chooseRepository.save(each);
        });
        cdOrderRepository.save(cdOrder);
        garbageChooses.forEach(each -> {
            each.setBaseOrder(order);
            chooseRepository.save(each);
        });

    }

    public void addCROrder(CROrder crOrder) {

    }

}
