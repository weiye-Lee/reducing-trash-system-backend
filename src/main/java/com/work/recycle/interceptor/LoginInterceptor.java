package com.work.recycle.interceptor;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.common.ResultCode;
import com.work.recycle.component.EncryptComponent;
import com.work.recycle.component.MyToken;
import com.work.recycle.exception.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Controller
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {
    @Autowired
    private EncryptComponent encrypt;
    @Autowired
    private ObjectMapper mapper;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.warn(mapper.writeValueAsString(request.getHeader(MyToken.AUTHORIZATION)));
        Optional.ofNullable(request.getHeader(MyToken.AUTHORIZATION))
                .map(auth->encrypt.decryptToken(auth))
                .ifPresentOrElse(token -> {
                    request.setAttribute(MyToken.UID, token.getUid());//键值对
                    request.setAttribute(MyToken.ROLE, token.getRole());
                    request.setAttribute(MyToken.AUTHCODE,token.getAuthcode());
                }, ()->{
                    throw new ApiException(ResultCode.UNAUTHORIZED);
                });
        return true;
    }
}

