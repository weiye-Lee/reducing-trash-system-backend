package com.work.recycle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.work.recycle.common.CommonResult;
import com.work.recycle.component.AuthCodeComponent;
import com.work.recycle.component.EncryptComponent;
import com.work.recycle.component.MyToken;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.User;
import com.work.recycle.entity.UserRole;
import com.work.recycle.repository.UserRepository;
import com.work.recycle.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("api/user/")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EncryptComponent encrypt;
    @Autowired
    private AuthCodeComponent authCodeComponent;
    @Autowired
    private RequestComponent requestComponent;
    @PostMapping("sentAuthCode")
    public Map sentAuthCode(@Param("phone") String phone, HttpServletResponse response) throws JsonProcessingException {
        String authcode = String.format("%06d", new Random().nextInt(1000000));
        authCodeComponent.sentTextMsg(phone,authcode);
        log.warn(authcode);
        // 创建包含验证码token
        MyToken token = new MyToken(authcode);
        log.warn(token.toString());
        String auth = encrypt.encryptToken(token);
        response.setHeader(MyToken.AUTHORIZATION, auth);//以键值对形式放入头中
        return Map.of("code","返回角色集合列表");
    }
    @PostMapping("checkAuthCode")
    public Map checkAuthCode(@Param("code") String code,HttpServletResponse response) {
        log.warn(code);
        log.warn(requestComponent.getAuthCode());
        if (code.equals(requestComponent.getAuthCode())) {
            int uid = 1; // 通过手机号查询用户表获得uid
            MyToken token = new MyToken(uid);
            String auth = encrypt.encryptToken(token);
            response.setHeader(MyToken.AUTHORIZATION,auth);
            return Map.of("code",List.of(UserRole.Role.CLEANER,UserRole.Role.FARMER));
        }

        else return Map.of("code","wrong");
    }

    @GetMapping("getGarbage")
    public CommonResult getGarbage() {
        return CommonResult.success(userService.getGarbage());
    }


    @PostMapping("updateUserInfo")
    public CommonResult updateUserInfo(@RequestBody User user) {
        return CommonResult.success(userService.updateUserInfo(user));
    }

    @GetMapping("getFCOrders")
    public CommonResult getFCOrders() {
        return CommonResult.success(userService.getFCOrders());
    }

}
