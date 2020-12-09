package com.work.recycle.controller;

import com.work.recycle.common.CommonResult;
import com.work.recycle.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/admin/")
public class AdminController {

    // TODO 2020/10/26 : 管理员端接口设计
    @PostMapping("add/Cleaner")
    public CommonResult addCleaner(User user) {
        return null;
    }
    @PostMapping("add/driver")
    public CommonResult addDriver(User user) {
        return null;
    }
    @PostMapping("add/recycleFirm")
    public CommonResult addRecycleFirm(User user) {
        return null;
    }
    @PostMapping("add/transferStation")
    public CommonResult addTransferStation(User user) {
        return null;
    }

    @GetMapping("get/Cleaner")
    public CommonResult getCleaner() {
        return null;
    }

    @GetMapping("get/Driver")
    public CommonResult getDriver() {
        return null;
    }

    @GetMapping("get/recycleFimr")
    public CommonResult getRecycleFirm() {
        return null;
    }

    @GetMapping("get/transferStation")
    public CommonResult getTransferStation() {
        return null;
    }

    @PostMapping("change/Cleaner")
    public CommonResult changeCleaner(int oldId , int newId , List<Integer> FarmerId) {
        return null;
    }

}
