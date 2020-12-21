package com.work.recycle.repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.FCOrder;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
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

    @Test
    void getByBaseOrder_AddressAndBaseOrder_InsertTime() {
        List<FCOrder> fcOrders = fcOrderRepository.getByAddressAndTime("平安街道", LocalDateTime.parse("2020-11-15T17:47:29"),LocalDateTime.parse("2020-11-15T" +

                " -- 18:51:30"));
        fcOrders.forEach(fcOrder -> {
            System.out.println(fcOrder.getBaseOrder().getId());
        });
    }
}