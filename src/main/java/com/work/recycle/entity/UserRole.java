package com.work.recycle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
// TODO 2020/10/14 : 修改用户实体，将角色属性加入到用户表中
public class UserRole {
    @Id
    // 自增，可以通过uuid对其进行处理
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public enum Role{
        FARMER,CLEANER,DREIVER,RECYCLEFIRM,ADMIN,LENADER
    }

    private Role role;

    @Column(length = 10)
    private String roleName;

    @ManyToOne
    private User user;
}
