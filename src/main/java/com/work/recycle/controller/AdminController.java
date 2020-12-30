package com.work.recycle.controller;

import com.work.recycle.common.CommonResult;
import com.work.recycle.common.ResultCode;
import com.work.recycle.controller.vo.SiftOrderVo;
import com.work.recycle.controller.vo.UserVo;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.User;
import com.work.recycle.exception.ApiException;
import com.work.recycle.service.AdminService;
import com.work.recycle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/")
public class AdminController {
    @Autowired
    private UserService userService;
    @Autowired
    private AdminService adminService;

    /**
     * 新增保洁员用户
     * @param user 用户基类 {
     *   "province": "黑龙江省",
     *   "city": "大庆市",
     *   "area": "双城区",
     *   "street": "平安街道",
     *   "village": "伟业村",
     *   "name": "测试用保洁员",                //不为空
     *   "IDNumber": "20207878549017382",
     *   "phoneNumber": "15941627201"          //不为空
     * }
     * @return 返回User{
     *   "code": 200,
     *   "message": "操作成功",
     *   "data": {
     *     "id": 69,
     *     "name": "回收企业",
     *     "phoneNumber": "15941627201",
     *     "role": "CLEANER",
     *     "province": "黑龙江省",
     *     "city": "大庆市",
     *     "area": "双城区",
     *     "street": "平安街道",
     *     "village": "伟业村",
     *     "usable": true
     *   }
     * }
     */
    @PostMapping("add/Cleaner")
    public CommonResult addCleaner(@RequestBody User user) {
        return CommonResult.success(adminService.addCleaner(user));
    }

    // TODO: 2020/12/21 司机和保洁员之间存在对应关系，是否在插入时体现出来
    /**
     * 新增司机司机用户
     * @param user the user
     * @return return the code message and the inserted user
     */
    @PostMapping("add/driver")
    public CommonResult addDriver(@RequestBody User user) {
        return CommonResult.success(adminService.addDriver(user));
    }

    /**
     * 新增回收企业用户
     * @param user the user
     * @return return the code message and the inserted user
     */
    @PostMapping("add/recycleFirm")
    public CommonResult addRecycleFirm(@RequestBody User user) {
        return CommonResult.success(adminService.addRecycleFirm(user));
    }

    /**
     * 新增中转站用户
     * @param user the user
     * @return return the code message and the inserted user
     */
    @PostMapping("add/transferStation")
    public CommonResult addTransferStation(@RequestBody User user) {
        return CommonResult.success(adminService.addTransferStation(user));
    }

    /**
     * 获取全部保洁员信息
     * @return the cleaner list
     */
    @GetMapping("get/Cleaner")
    public CommonResult getCleaner() {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getCleaner()));
    }


    /**
     * 获取全部司机信息
     * @return the driver list
     */
    @GetMapping("get/Driver")
    public CommonResult getDriver() {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getDriver()));
    }

    /**
     * 获取全部回收企业信息
     * @return the recycleFirm list
     */
    @GetMapping("get/recycleFirm")
    public CommonResult getRecycleFirm() {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getRecyclefirm()));
    }

    /**
     * 获取全部中转站信息
     * @return the transferStation list
     */
    @GetMapping("get/transferStation")
    public CommonResult getTransferStation() {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getTransferStation()));
    }

    /**
     * 获取指定id的保洁员所管辖的农户
     * @param id 保洁员id
     * @return the farmer list
     */
    @GetMapping("get/farmer/byCleanerId")
    public CommonResult getFarmerByCleaner(@Param("id") int id) {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getFarmersByCleanerId(id)));
    }

    /**
     * 获取指定id的司机所负责的保洁员
     * @param id 司机id
     * @return the cleaner list
     */
    @GetMapping("get/cleaner/byDriverId")
    public CommonResult getCleanerByDriverId(@Param("id") int id) {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getCleanersByDriverId(id)));
    }

    /**
     * 获取指定id的用户的基本信息
     * @param id user_id
     * @return {
     *   "code": 200,
     *   "message": "操作成功",
     *   "data": {
     *     "name": "安慕希",
     *     "phoneNumber": "15941627298",
     *     "addressVo": {
     *       "province": "黑龙江省",
     *       "city": "大庆市",
     *       "area": "双城区",
     *       "street": "平安街道",
     *       "village": "伟业村"
     *     }
     *   }
     * }
     */
    @GetMapping("get/user/info")
    public CommonResult getUserInfo(@Param("id") int id) {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getUserInfo(id)));
    }

    /**
     * 保洁员转让
     * @param oldId 要进行转让的保洁员
     * @param newId 要接收的保洁员
     * @param farmerIds 要转让的农户的id list
     * @return farmerIds.size()
     */
    @PostMapping("transfer/Cleaner/{oldId}/{newId}")
    public CommonResult transferCleaner(@PathVariable("oldId") int oldId , @PathVariable("newId") int newId , @RequestBody List<Integer> farmerIds) {
        return CommonResult.success(adminService.transferCleaner(oldId, newId, farmerIds));
    }

    /**
     * 司机转让
     * @param oldId 要进行转让的司机id
     * @param newId 要接收的司机id
     * @param cleanerIds 要转让的保洁员的id list
     * @return cleanerIds.size()
     */
    @PostMapping("transfer/Driver/{oldId}/{newId}")
    public CommonResult transferDriver(@PathVariable("oldId") int oldId , @PathVariable("newId") int newId , @RequestBody List<Integer> cleanerIds) {
        return CommonResult.success(adminService.transferDriver(oldId, newId, cleanerIds));
    }

    /**
     * 获取地区垃圾量统计
     * @param siftOrderVo 查询条件(address time）
     * @return commonResult
     */
    @PostMapping("get/garbage/statistic")
    public CommonResult getGarageStatistic(@RequestBody SiftOrderVo siftOrderVo) {
        return null;
    }

    /**
     * 获取地区减量信息表
     * @param siftOrderVo 查询条件
     * @return data
     */
    @PostMapping("get/garbage/reduce")
    public CommonResult getGarbageReduce(@RequestBody SiftOrderVo siftOrderVo) {
        return null;
    }

    @PostMapping("set/user/usable/{id}/{bool}")
    public CommonResult setUsable(@PathVariable("id") int id,@PathVariable("bool") Boolean bool) {
        return CommonResult.success(adminService.setUsable(id,bool));
    }

    /**
     * 设置企业回收价格
     * @param garbageList the garbage list that must include the recyclePrice
     *                    and recycleUnit
     * @return thr length of garbageList
     */
    @PostMapping("set/recycle/price")
    public CommonResult setRecyclePrice(@RequestBody List<Garbage> garbageList) {
        if (garbageList == null) {
            throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
        }
        return CommonResult.success(adminService.setRecyclePrice(garbageList));
    }

    /**
     * 这是建议保洁员回收价格
     * @param garbageList the garbage list that must include the suggestPrice
     *                    and suggestUnit
     * @return thr length of garbageList
     */
    @PostMapping("set/suggest/price")
    public CommonResult setSuggestPrice(@RequestBody List<Garbage> garbageList) {
        if (garbageList == null) {
            throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
        }
        return CommonResult.success(adminService.setSuggestPrice(garbageList));
    }

    /**
     * 插入一条垃圾
     * @param garbage 垃圾类（范畴，类别，单位，积分，回收价格，建议价格）
     * @return the garbage
     */
    @PostMapping("add/garbage")
    public CommonResult addRecycleGarbage(@RequestBody Garbage garbage) {
        return CommonResult.success(adminService.addGarbage(garbage));
    }


    /**
     * 设置设置指定垃圾的积分值
     * @param garbage id and score must not be null
     * @return the garbage
     */
    @PostMapping("set/score")
    public CommonResult setScore(@RequestBody Garbage garbage) {
        return CommonResult.success(adminService.setScore(garbage));
    }

}
