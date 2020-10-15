package com.work.recycle.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.xml.bind.v2.TODO;
import com.work.recycle.common.CommonResult;
import com.work.recycle.common.ResultCode;
import com.work.recycle.component.AuthCodeComponent;
import com.work.recycle.component.EncryptComponent;
import com.work.recycle.component.MyToken;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.entity.User;
import com.work.recycle.service.UserService;
import com.work.recycle.utils.PhoneUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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

    /**
     * 验证码发送接口
     *
     * @param phone    用户手机号
     * @param response 响应体 {
     *                 token ：验证码 uid
     *                 （不包含uid是判断为完成登录和登录完成的标准）
     * }
     * @return 返回手机号
     */
    @PostMapping("sentAuthCode")
    public CommonResult sentAuthCode(@Param("phone") String phone, HttpServletResponse response) {

        if (!PhoneUtil.isMobileNO(phone)) {
            return CommonResult.failed(ResultCode.VALIDATE_FAILED);
        }
        User user = userService.getUserByPhone(phone);
        if (user == null) {
            return CommonResult.failed(ResultCode.FORBIDDEN);
        }
        String authcode = String.format("%06d", new Random().nextInt(1000000));
        // 是否使用向手机发送验证码的接口~~~
        // authCodeComponent.sentTextMsg(phone,authcode);
        log.warn(authcode);
        String auth = encrypt.encryptToken(new MyToken(authcode, user.getId()));
        response.setHeader(MyToken.AUTHORIZATION, auth);
        return CommonResult.success(Map.of("phone", phone));
    }

    /**
     * 对用户输入的验证进行校验
     * @param code ：验证码
     * @param response ：响应体 {
     *                 token ：uid Role
     * }
     * @return 若验证成功 返回 User 对象
     */
    @PostMapping("checkAuthCode")
    public CommonResult checkAuthCode(@Param("code") String code, HttpServletResponse response) {
        log.warn(code);
        log.warn(requestComponent.getAuthCode());
        if (code.equals(requestComponent.getAuthCode())) {
            User user = userService.getUserById(requestComponent.getUid());
            String auth = encrypt.encryptToken(new MyToken(user.getRole(),user.getId()));
            response.setHeader(MyToken.AUTHORIZATION, auth);
            return CommonResult.success(Map.of("user", user));
        } else return CommonResult.failed(ResultCode.FORBIDDEN);
    }

    /**
     * 获得垃圾列表
     *
     * @return 成功 则返回垃圾集合
     */
    @GetMapping("getGarbage")
    public CommonResult getGarbage() {
        return CommonResult.success(userService.getGarbage());
    }


    /**
     * 更新用户信息
     *
     * @param user {
     *             String name,
     *             Sex sex,
     * }
     * @return 若成功返回修改后的用户对象
     */
    @PostMapping("updateUserInfo")
    public CommonResult updateUserInfo(@RequestBody User user) {
        return CommonResult.success(userService.updateUserInfo(user));
    }


    /**
     * 根据token中id获取用户基本信息
     *
     * @return user
     */
    @GetMapping("index")
    public Map getUser() {
        log.debug("{}", requestComponent.getUid());
        User user = userService.getUserById(requestComponent.getUid());
        if (user != null) {
            return Map.of("user", user);
        } else {
            return Map.of("{}", null);
        }

    }
}
