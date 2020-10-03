package com.work.recycle.service;

import com.work.recycle.entity.*;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecycleFirmService {

    @Autowired
    private GarbageRepository garbageRepository;
    @Autowired
    private GarbageChooseRepository chooseRepository;
    @Autowired
    private CROrderRepository crOrderRepository;
    @Autowired
    private RecycleFirmRepository firmRepository;
    @Autowired
    private CleanerRepository cleanerRepository;

    public void addCROrder(BaseOrder order, List<GarbageChoose> garbageChooses, Integer cleanerId) {
        CROrder crOrder = new CROrder();
        int uid = 1;
        RecycleFirm firm = firmRepository.getRecycleFirmById(uid);
        Cleaner cleaner = cleanerRepository.getCleanerById(cleanerId);
        crOrder.setBaseOrder(order);
        crOrder.setRecycleFirm(firm);
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
}
