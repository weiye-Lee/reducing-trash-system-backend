package com.work.recycle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.common.CommonResult;
import com.work.recycle.component.OrdersComponent;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.DTOrder;
import com.work.recycle.entity.GarbageChoose;
import com.work.recycle.service.TransferStationService;
import com.work.recycle.utils.SwitchUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("api/transferStation/")
public class TransferStationController {
    @Autowired
    private TransferStationService transferStationService;
    @Autowired
    private ObjectMapper mapper;

    @PostMapping("addDTOrder")
    public CommonResult addDTOrder(@Param("id") int id, @RequestBody BaseOrder baseOrder) {
        try {
            log.warn(mapper.writeValueAsString(baseOrder));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<GarbageChoose> garbageChooses = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        transferStationService.addDTOrder(baseOrder,garbageChooses,id);
        return CommonResult.success(null);
    }

    @GetMapping("getDTOrder")
    public CommonResult getDTOrder() {
        return CommonResult.success(transferStationService.getDTOrders());
    }
}
