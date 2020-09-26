package com.work.recycle.service;

import com.work.recycle.entity.*;
import com.work.recycle.repository.CROrderRepository;
import com.work.recycle.repository.GarbageChooseRepository;
import com.work.recycle.repository.GarbageRepository;
import com.work.recycle.repository.RecycleFirmRepository;
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

    public void addCROrder(BaseOrder order, List<GarbageChoose> garbageChooses, Cleaner cleaner) {
        CROrder crOrder = new CROrder();
        int uid = 1;
        RecycleFirm firm = firmRepository.getRecycleFirmById(uid);
        crOrder.setBaseOrder(order);
        crOrder.setRecycleFirm(firm);

        garbageChooses.forEach(each -> {
            int garbageId = each.getGarbage().getId();
            Garbage garbage = garbageRepository.findById(garbageId).get();
            chooseRepository.save(each);
        });
        crOrderRepository.save(crOrder);
        garbageChooses.forEach(each -> {
            each.setBaseOrder(order);
            chooseRepository.save(each);
        });

    }
}
