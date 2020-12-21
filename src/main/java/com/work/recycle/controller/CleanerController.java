package com.work.recycle.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.common.CommonResult;
import com.work.recycle.controller.vo.AddressVo;
import com.work.recycle.controller.vo.RankListVo;
import com.work.recycle.controller.vo.SiftOrderVo;
import com.work.recycle.entity.*;
import com.work.recycle.service.CleanerService;
import com.work.recycle.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Slf4j
@RequestMapping("api/cleaner/")
public class CleanerController {
    @Autowired
    private CleanerService cleanerService;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserService userService;

    /**
     * 获得农户提交待审核订单
     *
     * @return the fc orders checking
     */
    @PostMapping("getFCOrders/checking")
    public CommonResult getFCOrdersChecking(@Valid @RequestBody SiftOrderVo siftOrderVo) {
        return CommonResult.success(cleanerService.getFCOrderChecking(siftOrderVo));
    }


    /**
     * 获得农户提交审核完成订单
     *
     * @return 订单vo list
     */
    @PostMapping("getFCOrders/checked")
    public CommonResult getFCOrdersChecker(@Valid @RequestBody SiftOrderVo siftOrderVo) {
        return CommonResult.success(cleanerService.getFCOrderChecked(siftOrderVo));
    }

    /**
     * ------------ 弃用 -------------
     * 由于保洁员和农户之间是严格的一对多的关系，在创建时就已经完成接单，相当于自动接单
     * <p>
     * 接收农户订单
     *
     * @param id 订单id
     * @return null
     */
    @PostMapping("receive/order/{id}")
    public CommonResult receiveFCOrder(@PathVariable("id") int id) {
        return CommonResult.success(cleanerService.receiveFCOrder(id));
    }

    /**
     * 保洁员审核农户提交的订单
     *
     * @param baseOrder the baseOrder
     * @return null
     */
    @PostMapping("checkFCOrder")
    public CommonResult checkFCOrder(@RequestBody BaseOrder baseOrder) {
        List<GarbageChoose> garbageChooseList = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        cleanerService.checkFCOrder(baseOrder, garbageChooseList);
        return CommonResult.success(null);
    }

    /**
     * 添加保洁员 - 司机订单
     *
     * @param baseOrder the baseOrder
     * @return null
     */
    @PostMapping("addCDOrder")
    public CommonResult addCDOrder(@RequestBody BaseOrder baseOrder) {
        List<GarbageChoose> garbageChooses = baseOrder.getGarbageChooses();
        baseOrder.setGarbageChooses(null);
        cleanerService.addCDOrder(baseOrder, garbageChooses);
        return CommonResult.success(null);
    }


    /**
     * 获得基础订单信息
     * {
     * "code": 200,
     * "message": "操作成功",
     * "data": {
     * "id": 3,
     * "score": 485.0,
     * "checkStatus": false,
     * "remark": "管理员提交一条记录",
     * "garbageChooses": [
     * {
     * "id": 5,
     * "garbage": {
     * "id": 1,
     * "name": "纸壳、硬纸板",
     * "category": "可回收垃圾",
     * "type": "纸张类",
     * "score": 25.0,
     * "unit": "斤",
     * "picture": "http://localhost:8080/static/1.jpg"
     * },
     * "amount": 5.0
     * },
     * {
     * "id": 6,
     * "garbage": {
     * "id": 2,
     * "name": "废旧资料、旧报纸 ",
     * "category": "可回收垃圾",
     * "type": "纸张类",
     * "score": 60.0,
     * "unit": "斤",
     * "picture": "http://localhost:8080/static/2.jpg"
     * },
     * "amount": 6.0
     * }
     * ],
     * "insertTime": "2020-10-25T11:28:51",
     * "updateTime": "2020-10-25T11:28:51"
     * }
     * }
     */
    @GetMapping("getBaseOrderById")
    public CommonResult getBaseOrderById(@Param("id") int id) {
        return CommonResult.success(userService.getBaseOrderById(id));
    }

    /**
     * 获取积分和提交次数展示
     *
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
        Map map = Map.of("score", cleanerService.getScore());
        Map map1 = Map.of("times", cleanerService.getFCOrderTimes());
        List<Map> list = new ArrayList<>();
        list.add(map);
        list.add(map1);
        return CommonResult.success(list);
    }

    @PostMapping("getCDOrder/Checking")
    public CommonResult getCDOrderChecking(@RequestBody SiftOrderVo siftOrderVo) {
        return CommonResult.success(cleanerService.getCDOrderChecking(siftOrderVo));
    }

    @PostMapping("getCDOrder/Checked")
    public CommonResult getCDOrderChecker(@RequestBody SiftOrderVo siftOrderVo) {
        return CommonResult.success(cleanerService.getCDOrderChecked(siftOrderVo));
    }

    @PostMapping("getCROrders")
    public CommonResult getCROrders(@RequestBody SiftOrderVo siftOrderVo) {
        return CommonResult.success(cleanerService.getCROrders(siftOrderVo));
    }


    @PostMapping("addFarmer")
    public CommonResult addFarmer(@RequestBody User user) {
        return CommonResult.success(cleanerService.addFarmer(user));
    }


    @PostMapping("getRankList")
    public CommonResult getRankList(@RequestBody AddressVo addressVo) {
        List<Cleaner> cleaners = cleanerService.getRankList(addressVo);
        List<RankListVo> rankListVos = new ArrayList<>();
        cleaners.forEach(each -> rankListVos.add(
                new RankListVo(
                        each.getUser().getName()
                        , each.getScore()
                        , each.getId()
                        , AddressVo.getAddressVo(each.getUser())
                )
        ));
        return CommonResult.success(rankListVos);
    }

    /**
     * 保洁员获取管辖用户列表
     *
     * @return {
     * "code": 200,
     * "message": "操作成功",
     * "data": [
     * {
     * "phoneNumber": "13050496542",
     * "name": "农户姓名",
     * "id": 3
     * },
     * {
     * "phoneNumber": "15941627298",
     * "name": "安慕希",
     * "id": 7,
     * "village": "伟业村"
     * }
     * }
     */
    @GetMapping("getFarmers")
    public CommonResult getFarmer() {
        List<Map<String, Object>> list = new ArrayList<>();
        cleanerService.getFarmer()
                .forEach(farmer -> {
                    Map<String, Object> map = new HashMap();
                    map.put("name", farmer.getUser().getName());
                    map.put("id", farmer.getId());
                    map.put("village", farmer.getUser().getVillage());
                    map.put("phoneNumber", farmer.getUser().getPhoneNumber());
                    list.add(map);
                });
        return CommonResult.success(list);
    }

    /**
     * 获取某一个用户的详细信息
     *
     * @param id 农户id
     * @return {
     * "code": 200,
     * "message": "操作成功",
     *      "data": {
     *          "id": 7,
     *          "user": {
     *              "id": 7,
     *              "name": "安慕希",
     *              "phoneNumber": "15941627298",
     *              "role": "FARMER",
     *              "province": "黑龙江省",
     *              "city": "大庆市",
     *              "area": "双城区",
     *              "street": "平安街道",
     *              "village": "伟业村"
     *          },
     *          "familySize": 1,
     *          "score": 0.0
     *      }
     * }
     */
    @GetMapping("getFarmer/index")
    public CommonResult getFarmerIndex(int id) {
        return CommonResult.success(cleanerService.getFarmerIndex(id));
    }

    @GetMapping("getFarmerRanklist")
    public CommonResult getFarmerRanklist() {
        List<Farmer> farmers = cleanerService.getFarmerRankList();
        List<RankListVo> rankListVos = new ArrayList<>();
        farmers.forEach(each -> rankListVos.add(new RankListVo(each.getUser().getName(), each.getScore(), each.getId())));
        return CommonResult.success(rankListVos);
    }

    @PostMapping("removeCDOrders")
    public CommonResult removeCDOrders(@Param("id") int id) {
        return CommonResult.success(cleanerService.removeCDOrder(id));
    }

}
