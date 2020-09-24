package com.work.recycle.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
}