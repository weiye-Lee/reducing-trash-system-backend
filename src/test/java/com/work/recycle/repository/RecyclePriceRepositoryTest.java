package com.work.recycle.repository;

import com.work.recycle.entity.RecyclePrice;
import com.work.recycle.entity.RecyclePriceRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RecyclePriceRepositoryTest {
    @Autowired
    private RecyclePriceRepository listRepository;
    @Autowired
    private RecyclePriceRecordRepository recordRepository;

    // TODO: 2020/12/27 test it
    @Test
    void test_add() {
        RecyclePrice recyclePriceList = new RecyclePrice();
        recyclePriceList.setPrice(5.0);
        RecyclePrice recyclePriceList2 = new RecyclePrice();
        recyclePriceList2.setPrice(6.0);
        RecyclePriceRecord recyclePriceRecord = new RecyclePriceRecord();
        recordRepository.save(recyclePriceRecord);
        recyclePriceList.setRecyclePriceRecord(recyclePriceRecord);
        recyclePriceList2.setRecyclePriceRecord(recyclePriceRecord);
        List<RecyclePrice> list = new ArrayList<>();
        list.add(recyclePriceList);
        list.add(recyclePriceList2);
        listRepository.saveAll(list);
    }
}