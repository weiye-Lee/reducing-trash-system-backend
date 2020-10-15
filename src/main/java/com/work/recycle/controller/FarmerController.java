package com.work.recycle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.work.recycle.common.CommonResult;
import com.work.recycle.component.RequestComponent;
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
    @Autowired
    private RequestComponent requestComponent;


    /**
     * 插入 ”农户-保洁员订单“
     * @param baseOrder {
     *   "address": "位置信息",
     *   "remark": "备注信息",
     *   "garbageChooses": [
     *     {
     *       "garbage": {
     *         "id": 1      // 垃圾id信息
     *       },
     *       "amount": 5.5  // 垃圾数量
     *     },
     *     {
     *       "garbage": {
     *           "id":2
     *       },
     *       "amount": 1.2
     *     }
     *   ]
     * }
     * @return 返回null
     */
    @PostMapping("addFCOrder")
    public CommonResult addFCOrder(@RequestBody BaseOrder baseOrder) {
        List<GarbageChoose> garbageChooses = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        farmerService.addFCOrder(baseOrder,garbageChooses);
        return CommonResult.success(null);
    }

    /**
     * 获得积分数量
     * @return score
     */
    @GetMapping("getScore")
    public CommonResult getScore() {
        return CommonResult.success(farmerService.getScore());
    }

    /**
     * 获取订单列表
     * @return
     */
    @GetMapping("getFCOrders")
    public CommonResult getFCOrders() {
        return CommonResult.success(farmerService.getOrders());
    }


    /**
     * 获取投递次数
     * @return times
     */
    @GetMapping("getOrderTimes")
    public CommonResult getOrderTimes() {
        return CommonResult.success(farmerService.getOrderTimes());
    }

    /**
     * 获得基础订单信息
     * @param id 基础订单id
     * @return BaseOrder {
     *   "code": 200,
     *   "message": "操作成功",
     *   "data": {
     *     "id": 1,
     *     "score": 20000.0,
     *     "receiveStatus": false,
     *     "checkStatus": false,
     *     "address": "黑龙江省大庆市",
     *     "remark": "农户zai插入提交一条记录",
     *     "garbageChooses": [
     *       {
     *         "id": 1,
     *         "garbage": {
     *           "id": 1,
     *           "name": "二手飞机",
     *           "category": "可回收垃圾",
     *           "score": 1,
     *           "unit": "个",
     *           "insertTime": "2020-10-15T15:14:46",
     *           "updateTime": "2020-10-15T15:33:15"
     *         },
     *         "amount": 20.0
     *       },
     *       {
     *         "id": 2,
     *         "garbage": {
     *           "id": 2,
     *           "name": "厨余垃圾",
     *           "category": "不可回收垃圾",
     *           "score": 0,
     *           "unit": "斤",
     *           "insertTime": "2020-10-15T15:14:46",
     *           "updateTime": "2020-10-15T15:14:46"
     *         },
     *         "amount": 20.0
     *       }
     *     ],
     *     "insertTime": "2020-10-15T15:23:54",
     *     "updateTime": "2020-10-15T15:23:54"
     *   }
     * }
     *
     * Response code: 200; Time: 127ms; Content length: 570 bytes
     */
    @GetMapping("getBaseOrderById")
    public CommonResult getBaseOrderById(@Param("id") int id) {
        return CommonResult.success(farmerService.getBaseOrderById(id));
    }

    // TODO 2020/10/13 : 农户积分？提交次数？排行榜

    /**
     * 获取用户排行榜列表
     * @return 返回农户排行榜集合
     */
    @GetMapping("getRankList")
    public CommonResult getRankList() {
        return null;
    }
}