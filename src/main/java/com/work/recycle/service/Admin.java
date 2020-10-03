package com.work.recycle.service;

import com.work.recycle.entity.Cleaner;
import com.work.recycle.repository.CleanerRepository;
import com.work.recycle.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class Admin {
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private FarmerRepository farmerRepository;

    public void addCleaner(Cleaner cleaner) {

        cleanerRepository.save(cleaner);
    }


}
