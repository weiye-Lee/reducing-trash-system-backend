package com.work.recycle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.common.CommonResult;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.controller.vo.AddressVo;
import com.work.recycle.controller.vo.RankListVo;
import com.work.recycle.controller.vo.SiftOrderVo;
import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.FCOrder;
import com.work.recycle.entity.Farmer;
import com.work.recycle.entity.GarbageChoose;
import com.work.recycle.service.FarmerService;
import com.work.recycle.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("api/farmer/")
public class FarmerController {
    @Autowired
    private FarmerService farmerService;
    @Autowired
    private UserService userService;
    @Autowired
    private ObjectMapper mapper;


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
        try {
            log.warn(mapper.writeValueAsString(baseOrder));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<GarbageChoose> garbageChooses = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        farmerService.addFCOrder(baseOrder,garbageChooses);
        return CommonResult.success(null);
    }

    /**
     * 获取审核完成订单
     * @return the IndexOrderVo list、
     */
    @PostMapping("getFCOrder/checked")
    public CommonResult getFCOrderChecker(@RequestBody SiftOrderVo siftOrderVo) {
        return CommonResult.success(farmerService.getFCOrderChecked(siftOrderVo));
    }

    /**
     * 获取审核未完成订单
     * @return the IndexOrderVo list
     */
    @PostMapping("getFCOrder/checking")
    public CommonResult getFCOrderChecking(@Valid @RequestBody SiftOrderVo siftOrderVo) {
        return CommonResult.success(farmerService.getFCOrderChecking(siftOrderVo));
    }


    /**
     * 获取积分和提交次数
     * @return {
     *   "code": 200,
     *   "message": "操作成功",
     *   "data": [
     *     {
     *       "score": 0
     *     },
     *     {
     *       "times": 3
     *     }
     *   ]
     * }
     */
    @GetMapping("getOrderInfo")
    public CommonResult getOrderInfo() {
        Map map = Map.of("score",farmerService.getScore());
        Map map1 = Map.of("times",farmerService.getOrderTimes());
        List<Map> list = new ArrayList<>();
        list.add(map);
        list.add(map1);
        return CommonResult.success(list);
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
     */
    @GetMapping("getBaseOrderById")
    public CommonResult getBaseOrderById(@Param("id") int id) {
        return CommonResult.success(userService.getBaseOrderById(id));
    }


    /**
     * 获取积分前十名的用户
     * @return 返回农户排行榜集合
     */
    @PostMapping("getRankList")
    public CommonResult getRankList(@RequestBody AddressVo addressVo) {
        List<Farmer> farmers = farmerService.getRankList(addressVo);
        List<RankListVo> rankListVos = new ArrayList<>();
        farmers.forEach(each -> rankListVos.add(new RankListVo(each.getUser().getName(),each.getScore(), each.getId())));
        return CommonResult.success(rankListVos);
    }
    
    @PostMapping("removeFCOrder")
    public CommonResult removeFCOrder(@Param("id") int id) {
        return CommonResult.success(farmerService.removeFCOrder(id));
    }

    /**
     * 农户获取对应保洁员
     * @return {
     *     name : XXX,
     *     id : XX
     * }
     */
    @GetMapping("getCleaner")
    public CommonResult getCleaner() {
        return CommonResult.success(farmerService.getCleaner());
    }
}