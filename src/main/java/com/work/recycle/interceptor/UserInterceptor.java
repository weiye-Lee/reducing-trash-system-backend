package com.work.recycle.interceptor;

import com.work.recycle.common.ResultCode;
import com.work.recycle.component.RequestComponent;
import com.work.recycle.exception.ApiException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Controller
public class UserInterceptor implements HandlerInterceptor {
    @Autowired
    private RequestComponent requestComponent;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (requestComponent.getRole() == null) {
            throw new ApiException(ResultCode.UNAUTHORIZED);
        }
        return true;

    }
}
