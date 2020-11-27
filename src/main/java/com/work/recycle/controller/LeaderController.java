package com.work.recycle.controller;

import com.work.recycle.common.CommonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/leaner/")
public class LeaderController {
    @GetMapping("getUserNum")
    public CommonResult getUserNum() {
        return null;
    }
}

