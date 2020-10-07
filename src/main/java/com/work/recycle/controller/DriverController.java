package com.work.recycle.controller;

import com.work.recycle.common.CommonResult;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.CDOrder;
import com.work.recycle.entity.Driver;
import com.work.recycle.entity.GarbageChoose;
import com.work.recycle.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/driver/")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @GetMapping("receive/CDOrder/{id}")
    private CommonResult receiveCDOrder(@PathVariable("id") int id) {
        return CommonResult.success(driverService.receiveCDOrder(id));

    }

    @PostMapping("checkCDOrder")
    public CommonResult checkCDOrder(@RequestBody BaseOrder baseOrder) {
        List<GarbageChoose> garbageChooses = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        driverService.checkCDOrder(baseOrder,garbageChooses);
        return CommonResult.success(null);

    }

}
