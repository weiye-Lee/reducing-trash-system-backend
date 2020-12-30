package com.work.recycle.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.GarbageChoose;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class GarbageChooseRepositoryTest {
    @Autowired
    private GarbageChooseRepository chooseRepository;
    @Autowired
    private ObjectMapper mapper;
    @Test
    void getGarbageChooseById() throws JsonProcessingException {
        GarbageChoose g = chooseRepository.getGarbageChooseById(1);
        log.warn("{}",mapper.writeValueAsString(g));
    }

    @Test
    void test_saveChoose() {
        // 测试级联操作choose保存garbage

        Garbage g = new Garbage();
        // Garbage g1 = new Garbage();
        g.setName("粉笔盒");
        GarbageChoose garbageChoose = new GarbageChoose();
        // GarbageChoose garbageChoose1 = new GarbageChoose();
        garbageChoose.setGarbage(g);
        // garbageChoose1.setGarbage(g1);
        garbageChoose.setAmount(666);
        chooseRepository.save(garbageChoose);
    }

    @Test
    void deleteByBaseOrder_Id() {
        chooseRepository.deleteById(7);
    }
}