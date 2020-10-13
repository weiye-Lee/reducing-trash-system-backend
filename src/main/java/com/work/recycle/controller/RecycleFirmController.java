package com.work.recycle.controller;

import com.work.recycle.common.CommonResult;
import com.work.recycle.component.OrdersComponent;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.CROrder;
import com.work.recycle.entity.GarbageChoose;
import com.work.recycle.service.RecycleFirmService;
import com.work.recycle.utils.SwitchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/recycleFirm/")
public class RecycleFirmController {
    @Autowired
    private RecycleFirmService recycleFirmService;

    @PostMapping("addCROrder")
    public CommonResult addCROrder(BaseOrder baseOrder) {
        List<GarbageChoose> garbageChooses = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        recycleFirmService.addCROrder(baseOrder,garbageChooses);
        return CommonResult.success(null);
    }
}
