package com.work.recycle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.work.recycle.common.CommonResult;
import com.work.recycle.common.ResultCode;
import com.work.recycle.component.AuthCodeComponent;
import com.work.recycle.component.EncryptComponent;
import com.work.recycle.component.MyToken;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.User;
import com.work.recycle.entity.UserRole;
import com.work.recycle.exception.Asserts;
import com.work.recycle.repository.UserRepository;
import com.work.recycle.service.UserService;
import com.work.recycle.utils.PhoneUtil;
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
    public CommonResult sentAuthCode(@Param("phone") String phone, HttpServletResponse response) throws JsonProcessingException {

        // TODO 2020/10/14 : 手机号验证合法性
        // 手机号不合法
        // if (!PhoneUtil.isMobileNO(phone)) {
        //     return CommonResult.failed(ResultCode.FAILED);
        // }
        // if (userService.getUserByPhone(phone) == null) {
        //     return CommonResult.failed(ResultCode.FORBIDDEN);
        // }
        String authcode = String.format("%06d", new Random().nextInt(1000000));
        // 是否使用向手机发送验证码的接口~~~
        // authCodeComponent.sentTextMsg(phone,authcode);
        log.warn(authcode);
        String auth = encrypt.encryptToken(new MyToken(authcode));
        response.setHeader(MyToken.AUTHORIZATION, auth);
        return CommonResult.success(Map.of("code",phone));
    }
    @PostMapping("checkAuthCode")
    public CommonResult checkAuthCode(@Param("code") String code,HttpServletResponse response) {
        log.warn(code);
        log.warn(requestComponent.getAuthCode());
        if (code.equals(requestComponent.getAuthCode())) {
            int uid = 1; // 通过手机号查询用户表获得uid
            MyToken token = new MyToken(uid);
            String auth = encrypt.encryptToken(token);
            response.setHeader(MyToken.AUTHORIZATION,auth);
            return CommonResult.success(Map.of("code",List.of(UserRole.Role.CLEANER,UserRole.Role.FARMER)));
        }

        else return CommonResult.failed(ResultCode.FORBIDDEN);
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

    /**
     * 根据token中id获取用户基本信息
     * @return user
     */
    @GetMapping("index")
    public Map getUser(){
        log.debug("{}", requestComponent.getUid());
        User user = userService.getUserById(requestComponent.getUid());
        if(user!=null){
            return Map.of("user",user);
        }
        else{
            return Map.of("{}",null);
        }

    }
}
