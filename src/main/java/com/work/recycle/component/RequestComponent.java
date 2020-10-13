package com.work.recycle.component;


import com.work.recycle.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.List;

@Component
@Slf4j
public class RequestComponent {
    public int getUid(){
        return (int)RequestContextHolder.currentRequestAttributes()
                .getAttribute(MyToken.UID, RequestAttributes.SCOPE_REQUEST );
        //在请求范围内获取MyToken.UID
    }
    public List<UserRole.Role> getRole(){
        return (List<UserRole.Role>) RequestContextHolder.currentRequestAttributes()
                .getAttribute(MyToken.ROLE, RequestAttributes.SCOPE_REQUEST);
    }

    public String getAuthCode() {
        return (String)RequestContextHolder.currentRequestAttributes()
                .getAttribute(MyToken.AUTHCODE,RequestAttributes.SCOPE_REQUEST);
    }

}