package com.work.recycle.component;

import com.work.recycle.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MyToken implements Serializable {
    public static final String AUTHORIZATION = "Authorization";
    public static final String UID = "uid";
    public static final String ROLES = "roles";
    public static final String ROLE = "role";
    public static final String AUTHCODE = "authcode";


    private String authcode;
    private List<UserRole.Role> roles;
    private UserRole.Role role;
    private Integer uid;

    /**
     * 手机验证码token，登录时前后端交互进行验证
     *
     * @param authcode 验证码
     */
    public MyToken(String authcode) {
        this.authcode = authcode;
    }

    /**
     * 一个用户多个角色，返回一个角色集合
     *
     * @param roles 角色列表
     * @param uid   the uid
     */
    public MyToken(List<UserRole.Role> roles,Integer uid) {
        this.roles = roles;
        this.uid = uid;
    }


    /**
     * 用户选择后用于前后端数据交换传递的身份验证的json
     *
     * @param role 单个角色
     * @param uid  the uid
     */
    public MyToken(UserRole.Role role,Integer uid) {
        this.role = role;
        this.uid = uid;
    }

}
