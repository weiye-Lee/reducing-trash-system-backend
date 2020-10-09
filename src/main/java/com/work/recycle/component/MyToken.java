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
    public static final String ROLE = "role";
    public static final String AUTHCODE = "authcode";

    private String authcode;
    private UserRole.Role role;
    private Integer uid;

    public MyToken(String authcode) {
        this.authcode = authcode;
    }

    public MyToken(Integer uid) {
        this.uid = uid;
    }
}
