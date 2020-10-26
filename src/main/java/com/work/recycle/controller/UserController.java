package com.work.recycle.controller;

import com.work.recycle.common.CommonResult;
import com.work.recycle.common.ResultCode;
import com.work.recycle.component.EncryptComponent;
import com.work.recycle.component.MyToken;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.entity.User;
import com.work.recycle.exception.ApiException;
import com.work.recycle.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/user/")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EncryptComponent encrypt;
    @Autowired
    private RequestComponent requestComponent;
    @Autowired
    private PasswordEncoder encoder;

    /**
     * ---------- <弃用> ------------
     * 验证码发送接口
     *
     * @param phone    用户手机号
     * @param response 响应体 {
     *                 token ：验证码 uid
     *                 （不包含uid是判断为完成登录和登录完成的标准）
     *                 }
     * @return 返回手机号
     */
    // @PostMapping("sentAuthCode")
    // public CommonResult sentAuthCode(@Param("phone") String phone, HttpServletResponse response) {
    //     if (!PhoneUtil.isMobileNO(phone)) {
    //         return CommonResult.failed(ResultCode.VALIDATE_FAILED);
    //     }
    //     User user = userService.getUserByPhone(phone);
    //     if (user == null) {
    //         return CommonResult.failed(ResultCode.FORBIDDEN);
    //     }
    //     String authcode = String.format("%06d", new Random().nextInt(1000000));
    //     // 是否使用向手机发送验证码的接口~~~
    //     // authCodeComponent.sentTextMsg(phone,authcode);
    //     log.warn(authcode);
    //     String auth = encrypt.encryptToken(new MyToken(authcode, user.getId()));
    //     response.setHeader(MyToken.AUTHORIZATION, auth);
    //     return CommonResult.success(Map.of("phone", phone));
    // }

    /**
     * ----------- <弃用> -------------
     * 对用户输入的验证进行校验
     *
     * @param /code     ：验证码
     * @param response ：响应体 {
     *                 token ：uid Role
     *                 }
     * @return 若验证成功 返回 User 对象
     */
    // @PostMapping("checkAuthCode")
    // public CommonResult checkAuthCode(@Param("code") String code, HttpServletResponse response) {
    //     log.warn(code);
    //     log.warn(requestComponent.getAuthCode());
    //     if (code.equals(requestComponent.getAuthCode())) {
    //         User user = userService.getUserById(requestComponent.getUid());
    //         String auth = encrypt.encryptToken(new MyToken(user.getRole(), user.getId()));
    //         response.setHeader(MyToken.AUTHORIZATION, auth);
    //         return CommonResult.success(Map.of("user", user));
    //     } else return CommonResult.failed(ResultCode.FORBIDDEN);
    // }

    /**
     * 用户登录接口
     * @param loginUser {
     *   "phoneNumber": "13050496540",
     *   "password": "123456"
     * }
     * @param response 响应体
     * @return User
     */
    @PostMapping("login")
    public CommonResult userLogin(@RequestBody User loginUser, HttpServletResponse response) {
        User user = Optional.ofNullable(userService.getUserByPhone(loginUser.getPhoneNumber()))
                .filter(u -> encoder.matches(loginUser.getPassword(), u.getPassword()))
                .orElseThrow(() -> new ApiException(ResultCode.UNAUTHORIZED));

        MyToken token = new MyToken(user.getRole(), user.getId());
        String auth = encrypt.encryptToken(token);
        response.setHeader(MyToken.AUTHORIZATION, auth);
        log.debug("{}", "登陆成功");
        return CommonResult.success(Map.of("user",user));
    }

    /**
     * 获得垃圾列表
     *
     * @return 成功 则返回垃圾集合
     */
    @GetMapping("getRecycleGarbage")
    public CommonResult getRecycleGarbage() {
        return CommonResult.success(userService.getRecycleGarbage());
    }

    @GetMapping("getUnRecycleGarbage")
    public CommonResult getUnRecycleGarbage() {
        return CommonResult.success(userService.getUnRecycleGarbage());
    }

    @GetMapping("getSoilGarbage")
    public CommonResult getSoilGarbage() {
        return CommonResult.success(userService.getSoilGarbage());
    }


    /**
     * 更新用户信息
     *
     * @param user {
     *             String name,
     *             Sex sex,
     *             }
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

    @PostMapping("updatePsw")
    public CommonResult updatePsw(@Param("password") String password) {
        return CommonResult.success(userService.updatePsw(password));
    }
}
