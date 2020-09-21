package com.work.recycle.service;

import com.work.recycle.repository.FarmerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FarmerService {
    @Autowired
    private FarmerRepository farmerRepository;

    public int getScore(int id) {
        return 1;
    }
}
