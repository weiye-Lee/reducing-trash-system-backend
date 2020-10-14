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

    /**
     * 获得农户提交待审核订单
     *
     * @return the fc orders checking
     */
    @GetMapping("getFCOrders/checking")
    public CommonResult getFCOrdersChecking() {
        return CommonResult.success(cleanerService.getFCOrders());
    }


    // TODO 2020/10/14 :  获取审核完成订单

    @GetMapping("getFCOrders/checked")
    public CommonResult getFCOrdersChecker() {
        return CommonResult.success(null);
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
        cleanerService.checkFCOrder(baseOrder,garbageChooseList);
        return CommonResult.success(null);
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
        cleanerService.addCDOrder(baseOrder,garbageChooses);
        return CommonResult.success(null);
    }

    @GetMapping("getScore")
    public CommonResult getScore() {
        return CommonResult.success(cleanerService.getScore());
    }

    @GetMapping("getOrderTimes")
    public CommonResult getOrderTimes() {
        return CommonResult.success(cleanerService.getFCOrderTimes());
    }


    // TODO 2020/10/14 : 获取保洁员排排行
    @GetMapping("getRankList")
    public CommonResult getRankList() {
        return null;
    }
}
