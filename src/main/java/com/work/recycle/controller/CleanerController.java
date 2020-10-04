package com.work.recycle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.common.CommonResult;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.CDOrder;
import com.work.recycle.entity.FCOrder;
import com.work.recycle.entity.GarbageChoose;
import com.work.recycle.repository.CleanerRepository;
import com.work.recycle.service.CleanerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("api/cleaner/")
public class CleanerController {
    @Autowired
    private CleanerService cleanerService;
    @Autowired
    private ObjectMapper mapper;

    @GetMapping("getFCOrders")
    public CommonResult getFCOrders() {
        return CommonResult.success(cleanerService.getFCOrders());
    }

    @PostMapping("receive/order/{id}")
    public CommonResult receiveFCOrder(@PathVariable("id") int id) {
        return CommonResult.success(cleanerService.receiveFCOrder(id));
    }

    @PostMapping("check/order")
    public CommonResult checkFCOrder(@RequestBody FCOrder fcOrder) {
        try {
            log.warn(mapper.writeValueAsString(fcOrder));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        BaseOrder baseOrder = fcOrder.getBaseOrder();
        List<GarbageChoose> garbageChooseList = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        return CommonResult.success(cleanerService.checkFCOrder(baseOrder,garbageChooseList));
    }

    @PostMapping("addCDOrder")
    public CommonResult addCDOrder(@RequestBody BaseOrder baseOrder) {
        try {
            log.warn(mapper.writeValueAsString(baseOrder));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<GarbageChoose> garbageChooses = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        return CommonResult.success(cleanerService.addCDOrder(baseOrder,garbageChooses));
    }

    @GetMapping("getScore")
    public CommonResult getScore() {
        return null;
    }

    @GetMapping("getOrderTimes")
    public CommonResult getOrderTimes() {
        return null;
    }
}
