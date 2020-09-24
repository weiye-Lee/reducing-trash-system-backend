package com.work.recycle.service;

import com.work.recycle.entity.Cleaner;
import com.work.recycle.entity.FCOrder;
import com.work.recycle.repository.CleanerRepository;
import com.work.recycle.repository.FCOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CleanerService {
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private FCOrderRepository fcOrderRepository;

    public int getFCOrderTime(int id) {
        return fcOrderRepository.getCleanerFCOrderTimesById(id);
    }

    public List<FCOrder> getFCOrdersById(int id) {
        return null;
    }

    public void addCleaner(Cleaner cleaner) {

        cleanerRepository.save(cleaner);
    }


}
