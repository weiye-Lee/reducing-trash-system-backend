package com.work.recycle.controller;

import com.work.recycle.common.CommonResult;
import com.work.recycle.common.ResultCode;
import com.work.recycle.component.OrdersComponent;
import com.work.recycle.controller.vo.SiftOrderVo;
import com.work.recycle.entity.*;
import com.work.recycle.exception.ApiException;
import com.work.recycle.service.RecycleFirmService;
import com.work.recycle.service.UserService;
import com.work.recycle.utils.SwitchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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

    @PostMapping("list/CROrder/checked")
    public CommonResult getCROrderChecked(@RequestBody SiftOrderVo siftOrderVo) {
        return CommonResult.success(recycleFirmService.getCROrderChecked(siftOrderVo));
    }
    @PostMapping("list/CROrder/checking")
    public CommonResult getCROrderChecking(@RequestBody SiftOrderVo siftOrderVo) {
        return CommonResult.success(recycleFirmService.getCROrderChecking(siftOrderVo));
    }

    /**
     * 获取cr订单
     * @param id the crorder_id
     * @return the common result
     */
    @GetMapping("get/CROrder")
    public CommonResult getCROrder(@Param("id") int id) {
        CROrder crOrder = recycleFirmService.getCROrder(id);
        Cleaner cleaner = new Cleaner();
        RecycleDriver recycleDriver = new RecycleDriver();
//        cleaner.getUser().setName(crOrder.getCleaner().getUser().getName());
        String driverName = Optional.ofNullable(crOrder)
                .map(CROrder::getRecycleDriver)
                .map(RecycleDriver::getName)
                .orElse("未选择");
        String cleanerName = Optional.ofNullable(crOrder)
                .map(CROrder::getCleaner)
                .map(Cleaner::getUser)
                .map(User::getName)
                .orElse("未选择");
        recycleDriver.setName(driverName);
        User user = new User();
        user.setName(cleanerName);
        cleaner.setUser(user);
//        crOrder.setRecycleFirm(null);
        crOrder.setCleaner(cleaner);
        crOrder.setRecycleDriver(recycleDriver);
        return CommonResult.success(crOrder);
    }
}
