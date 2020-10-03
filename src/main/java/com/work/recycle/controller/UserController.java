package com.work.recycle.controller;

import com.work.recycle.common.CommonResult;
import com.work.recycle.component.AuthCodeComponent;
import com.work.recycle.component.EncryptComponent;
import com.work.recycle.component.MyToken;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.User;
import com.work.recycle.entity.UserRole;
import com.work.recycle.service.UserService;
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
    @PostMapping("sentAudhCode")
    public Map sentAuthCode(@Param("phone") String phone, HttpServletResponse response) {
        String authcode = String.format("%06d", new Random().nextInt(1000000));
        // authCodeComponent.sentTextMsg(phone,authcode);
        log.warn(authcode);
        // 通过验证码，用户角色，uid构造token，返回给前端
        MyToken token = new MyToken(authcode);
        // 对token进行加密
        String auth = encrypt.encryptToken(token);
        response.setHeader(MyToken.AUTHCODE, auth);//以键值对形式放入头中
        return Map.of("code","李伟业呕吼");
    }

    @PostMapping("checkAuthCode")
    public Boolean checkAudhCode(@Param("authCode") String authCode) {
        return authCode.equals(requestComponent.getAutoCode());
    }

    @GetMapping("getGarbage")
    public CommonResult getGarbage() {
        return CommonResult.success(userService.getGarbage());
    }

}
