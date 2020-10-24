package com.work.recycle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.common.CommonResult;
import com.work.recycle.controller.vo.RankListVo;
import com.work.recycle.entity.*;
import com.work.recycle.repository.CleanerRepository;
import com.work.recycle.service.CleanerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@Slf4j
@RequestMapping("api/cleaner/")
public class CleanerController {
    @Autowired
    private CleanerService cleanerService;
    @Autowired
    private ObjectMapper mapper;

    /**
     * 获得农户提交待审核订单
     *
     * @return the fc orders checking
     */
    @GetMapping("getFCOrders/checking")
    public CommonResult getFCOrdersChecking() {
        return CommonResult.success(cleanerService.getFCOrderChecking());
    }
    

    /**
     * 获得农户提交审核完成订单
     * @return 订单vo list
     */
    @GetMapping("getFCOrders/checked")
    public CommonResult getFCOrdersChecker() {
        return CommonResult.success(cleanerService.getFCOrderChecked());
    }

    /**
     * ------------ 弃用 -------------
     * 由于保洁员和农户之间是严格的一对多的关系，在创建时就已经完成接单，相当于自动接单
     *
     * 接收农户订单
     * @param id 订单id
     * @return null
     */
    @PostMapping("receive/order/{id}")
    public CommonResult receiveFCOrder(@PathVariable("id") int id) {
        return CommonResult.success(cleanerService.receiveFCOrder(id));
    }

    /**
     * 保洁员审核农户提交的订单
     * @param baseOrder the baseOrder
     * @return null
     */
    @PostMapping("checkFCOrder")
    public CommonResult checkFCOrder(@RequestBody BaseOrder baseOrder) {
        List<GarbageChoose> garbageChooseList = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        cleanerService.checkFCOrder(baseOrder,garbageChooseList);
        return CommonResult.success(null);
    }

    /**
     * 添加保洁员 - 司机订单
     * @param baseOrder the baseOrder
     * @return null
     */
    @PostMapping("addCDOrder")
    public CommonResult addCDOrder(@RequestBody BaseOrder baseOrder) {
        try {
            log.warn(mapper.writeValueAsString(baseOrder));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        List<GarbageChoose> garbageChooses = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        cleanerService.addCDOrder(baseOrder,garbageChooses);
        return CommonResult.success(null);
    }

    /**
     * 获得保洁员积分数
     * @return （int）积分
     */
    @GetMapping("getScore")
    public CommonResult getScore() {
        return CommonResult.success(cleanerService.getScore());
    }

    // TODO 2020/10/17 : 保洁员涉及多个订单，返回那个订单的次数
    @GetMapping("getOrderTimes")
    public CommonResult getOrderTimes() {
        return CommonResult.success(cleanerService.getFCOrderTimes());
    }


    // TODO 2020/10/14 : 进行测试
    @GetMapping("getRankList")
    public CommonResult getRankList() {
        List<Cleaner> cleaners = cleanerService.getRankList();
        List<RankListVo> rankListVos = new ArrayList<>();
        cleaners.forEach(each -> rankListVos.add(new RankListVo(each.getUser().getName(),each.getScore(), each.getId())));
        return CommonResult.success(rankListVos);
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
        return CommonResult.success(cleanerService.getBaseOrderById(id));
    }

    @GetMapping("getCDOrder/Checking")
    public CommonResult getCDOrderChecking() {
        return CommonResult.success(cleanerService.getCDOrderChecking());
    }

    @GetMapping("getCDOrder/Checked")
    public CommonResult getCDOrderChecker() {
        return CommonResult.success(cleanerService.getCDOrderChecked());
    }


}
