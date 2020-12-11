package com.work.recycle.controller;

import com.work.recycle.common.CommonResult;
import com.work.recycle.controller.vo.UserVo;
import com.work.recycle.entity.User;
import com.work.recycle.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/admin/")
public class AdminController {
    @Autowired
    private AdminService adminService;

    // TODO 2020/10/26 : 管理员端接口设计
    @PostMapping("add/Cleaner")
    public CommonResult addCleaner(@RequestBody User user) {
        return CommonResult.success(adminService.addCleaner(user));
    }
    @PostMapping("add/driver")
    public CommonResult addDriver(@RequestBody User user) {
        return CommonResult.success(adminService.addDriver(user));
    }
    @PostMapping("add/recycleFirm")
    public CommonResult addRecycleFirm(@RequestBody User user) {
        return CommonResult.success(adminService.addRecycleFirm(user));
    }
    @PostMapping("add/transferStation")
    public CommonResult addTransferStation(@RequestBody User user) {
        return CommonResult.success(adminService.addTransferStation(user));
    }

    /**
     * 获取全部保洁员信息
     * @return
     */
    @GetMapping("get/Cleaner")
    public CommonResult getCleaner() {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getCleaner()));
    }


    /**
     * 获取全部司机信息
     * @return
     */
    @GetMapping("get/Driver")
    public CommonResult getDriver() {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getDriver()));
    }

    @GetMapping("get/recycleFirm")
    public CommonResult getRecycleFirm() {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getRecyclefirm()));
    }

    @GetMapping("get/transferStation")
    public CommonResult getTransferStation() {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getTransferStation()));
    }

    @GetMapping("get/farmer/byCleanerId")
    public CommonResult getFarmerByCleaner(@Param("id") int id) {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getFarmersByCleanerId(id)));
    }

    @GetMapping("get/cleaner/byDriverId")
    public CommonResult getCleanerByDriverId(@Param("id") int id) {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getCleanersByDriverId(id)));
    }
    
    @GetMapping("get/user/info")
    public CommonResult getUserInfo(@Param("id") int id) {
        return CommonResult.success(UserVo.conversionUserVo(adminService.getUserInfo(id)));
    }


    @PostMapping("transfer/Cleaner/{oldId}/{newId}")
    public CommonResult transferCleaner(@PathVariable("oldId") int oldId , @PathVariable("newId") int newId , @RequestBody List<Integer> farmerIds) {
        return CommonResult.success(adminService.transferCleaner(oldId, newId, farmerIds));
    }

    @PostMapping("transfer/Driver/{oldId}/{newId}")
    public CommonResult transferDriver(@PathVariable("oldId") int oldId , @PathVariable("newId") int newId , @RequestBody List<Integer> cleanerIds) {
        return CommonResult.success(adminService.transferDriver(oldId, newId, cleanerIds));
    }

}
