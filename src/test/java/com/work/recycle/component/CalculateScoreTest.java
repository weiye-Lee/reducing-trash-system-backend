package com.work.recycle.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.GarbageChoose;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class CalculateScoreTest {
    @Autowired
    private CalculateScore calculateScore;

    @Test
    void getScore() throws JsonProcessingException {
        BaseOrder order = new BaseOrder();
        order.setRemark("nothing to say");
        order.setAddress("火星");
        Garbage g = new Garbage();
        Garbage g2 = new Garbage();
        g.setId(1);
        g2.setId(2);

        GarbageChoose garbageChoose = new GarbageChoose();
        garbageChoose.setGarbage(g);
        GarbageChoose garbageChoose1 = new GarbageChoose();
        garbageChoose1.setGarbage(g2);
        List<GarbageChoose> list = new ArrayList<>();
        list.add(garbageChoose);
        list.add(garbageChoose1);

        calculateScore.getScore(list);
    }
}