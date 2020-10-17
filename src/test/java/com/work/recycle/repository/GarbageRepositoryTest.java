package com.work.recycle.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.controller.vo.GarbageVo;
import com.work.recycle.entity.Garbage;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j

class GarbageRepositoryTest {
    @Autowired
    private GarbageRepository garbageRepository;
    @Autowired
    private ObjectMapper mapper;
    @Test
    void test_addGarbage() {
        Garbage g = new Garbage();
        g.setName("水瓶子");
        Garbage g2 = new Garbage();
        g2.setName("二手飞机");
        garbageRepository.save(g);
        garbageRepository.save(g2);
    }

    @Test
    void test_getGarbage() throws JsonProcessingException {
        Garbage garbage = garbageRepository.getGarbageById(1);
        log.warn("{}",mapper.writeValueAsString(garbage));
    }

    @Test
    void getGarbageByType() {
        List<Garbage> garbageList = garbageRepository.getGarbageByType(GarbageVo.UNRECYCLE_CATEGORY,"农药包装");
        try {
            log.warn(mapper.writeValueAsString(garbageList));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}