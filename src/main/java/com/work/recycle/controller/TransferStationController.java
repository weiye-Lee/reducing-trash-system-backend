package com.work.recycle.controller;

import com.work.recycle.common.CommonResult;
import com.work.recycle.component.OrdersComponent;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.DTOrder;
import com.work.recycle.entity.GarbageChoose;
import com.work.recycle.service.TransferStationService;
import com.work.recycle.utils.SwitchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.AccessType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/transferStation/")
public class TransferStationController {
    @Autowired
    private TransferStationService transferStationService;


    @PostMapping("addDTOrder")
    public CommonResult addDTOrder(BaseOrder baseOrder) {
        List<GarbageChoose> garbageChooses = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        transferStationService.addDTOrder(baseOrder,garbageChooses);
        return CommonResult.success(null);
    }
}
