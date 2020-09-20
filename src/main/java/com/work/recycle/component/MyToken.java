package com.work.recycle.component;

import com.work.recycle.entity.UserRole;

import java.util.List;

public class MyToken {
    public static final String AUTHORIZATION = "Authorization";
    public static final String UID = "uid";
    public static final String ROLE = "roles";
    public static final String AUTHCODE = "authcode";
    private String authcode;
    private List<UserRole.Role> roles;
    private Integer uid;
}
