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

    /**
     * -------- 弃用 --------
     * 由于司机与保洁员之间时严格的一对多的关系，相当于自动接单
     *
     *
     * 司机接收保洁员订单
     * @param id 订单id
     * @return 用户uid
     */
    @GetMapping("receive/CDOrder/{id}")
    private CommonResult receiveCDOrder(@PathVariable("id") int id) {
        return CommonResult.success(driverService.receiveCDOrder(id));

    }

    /**
     * 司机审核保洁员农户订单
     * @param baseOrder {
     *   "id": 17,               // 基础订单 id
     *   "garbageChooses": [
     *     {
     *       "id": 34,           // 垃圾选择 id
     *       "garbage": {
     *         "id": 1           // 垃圾id
     *       },
     *       "amount": 10.3      // 垃圾数量
     *     },
     *     {
     *       "id": 35,
     *       "garbage": {
     *         "id": 2
     *       },
     *       "amount": 1.2
     *     }
     *   ]
     * }
     * @return null
     */
    @PostMapping("checkCDOrder")
    public CommonResult checkCDOrder(@RequestBody BaseOrder baseOrder) {
        List<GarbageChoose> garbageChooses = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        driverService.checkCDOrder(baseOrder,garbageChooses);
        return CommonResult.success(null);
    }

}
