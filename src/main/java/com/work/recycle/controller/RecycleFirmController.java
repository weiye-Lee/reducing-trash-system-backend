package com.work.recycle.controller;

import com.work.recycle.common.CommonResult;
import com.work.recycle.common.ResultCode;
import com.work.recycle.component.OrdersComponent;
import com.work.recycle.controller.vo.SiftOrderVo;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.CROrder;
import com.work.recycle.entity.GarbageChoose;
import com.work.recycle.exception.ApiException;
import com.work.recycle.service.RecycleFirmService;
import com.work.recycle.service.UserService;
import com.work.recycle.utils.SwitchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/recycleFirm/")
public class RecycleFirmController {
    @Autowired
    private RecycleFirmService recycleFirmService;
    @Autowired
    private UserService userService;

    @PostMapping("addCROrder")
    public CommonResult addCROrder(@RequestBody BaseOrder baseOrder, @Param("id") int id) {
        List<GarbageChoose> garbageChooses = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        recycleFirmService.addCROrder(baseOrder,garbageChooses,id);
        return CommonResult.success(null);
    }

    @PostMapping("getCROrders")
    public CommonResult getCROrders(@RequestBody SiftOrderVo siftOrderVo) {
        return CommonResult.success(recycleFirmService.getCROrders(siftOrderVo));
    }

    @GetMapping("getBaseOrderById")
    public CommonResult getBaseOrderById(@Param("id") int id) {
        return CommonResult.success(userService.getBaseOrderById(id));
    }

    @GetMapping("getCleaner")
    public CommonResult getCleaner(@Param("id") int id) {
        return CommonResult.success(recycleFirmService.getCleanerNameById(id));
    }

    @PostMapping("check/CROrder")
    public CommonResult checkCROrder(@RequestBody BaseOrder baseOrder) {
        List<GarbageChoose> garbageChooses = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        return CommonResult.success(recycleFirmService.checkCROrder(baseOrder,garbageChooses));
    }
}
