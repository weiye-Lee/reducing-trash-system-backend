package com.work.recycle.repository;

import com.work.recycle.entity.RecyclePriceList;
import com.work.recycle.entity.RecyclePriceRecord;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class RecyclePriceListRepositoryTest {
    @Autowired
    private RecyclePriceListRepository listRepository;
    @Autowired
    private RecyclePriceRecordRepository recordRepository;

    // TODO: 2020/12/27 test it
    @Test
    void test_add() {
        RecyclePriceList recyclePriceList = new RecyclePriceList();
        recyclePriceList.setPrice(5.0);
        RecyclePriceList recyclePriceList2 = new RecyclePriceList();
        recyclePriceList2.setPrice(6.0);
        RecyclePriceRecord recyclePriceRecord = new RecyclePriceRecord();
        recordRepository.save(recyclePriceRecord);
        recyclePriceList.setRecyclePriceRecord(recyclePriceRecord);
        recyclePriceList2.setRecyclePriceRecord(recyclePriceRecord);
        List<RecyclePriceList> list = new ArrayList<>();
        list.add(recyclePriceList);
        list.add(recyclePriceList2);
        listRepository.saveAll(list);
    }
}