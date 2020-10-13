package com.work.recycle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.work.recycle.common.CommonResult;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.FCOrder;
import com.work.recycle.entity.GarbageChoose;
import com.work.recycle.service.FarmerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("api/farmer/")
public class
FarmerController {
    @Autowired
    private FarmerService farmerService;


    @PostMapping("addFCOrder")
    public CommonResult addFCOrder(@RequestBody BaseOrder baseOrder) {
        List<GarbageChoose> garbageChooses = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        farmerService.addFCOrder(baseOrder,garbageChooses);
        return CommonResult.success(null);
    }

    @GetMapping("getScore")
    public CommonResult getScore() {
        return CommonResult.success(farmerService.getScore());
    }

    @GetMapping("getFCOrders")
    public CommonResult getFCOrders() {
        return CommonResult.success(farmerService.getOrders());
    }


    @GetMapping("getOrderTimes")
    public CommonResult getOrderTimes() {
        return CommonResult.success(farmerService.getOrderTimes());
    }

    @GetMapping("getBaseOrderById")
    public CommonResult getBaseOrderById(@Param("id") int id) {
        return CommonResult.success(farmerService.getBaseOrderById(id));
    }

    // TODO 2020/10/13 : 农户积分？提交次数？排行榜
    @GetMapping("getRankList")
    public CommonResult getRankList() {
        return null;
    }

}