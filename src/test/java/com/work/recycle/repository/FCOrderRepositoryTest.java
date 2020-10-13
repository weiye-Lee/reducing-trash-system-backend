package com.work.recycle.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.FCOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class FCOrderRepositoryTest {
    @Autowired
    private FCOrderRepository fcOrderRepository;
    @Autowired
    private ObjectMapper mapper;


    @Test
    void getFCOrdersByCleaner() {
        List<FCOrder> fcOrders = fcOrderRepository.getCleanerFCOrdersById(5);
        Optional.ofNullable(fcOrders).ifPresentOrElse(a -> a.forEach(
                each -> {
                    try {
                        log.warn(mapper.writeValueAsString(each));
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                }
                )
                , () -> log.warn("null"));
    }
}