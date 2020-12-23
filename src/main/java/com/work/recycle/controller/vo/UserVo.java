package com.work.recycle.controller.vo;

import com.work.recycle.entity.User;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户基本信息
 * name 姓名
 * phoneNumber 手机号
 * addressVo 地理位置
 * usable 账号是否可用
 */
@Data
public class UserVo {
    private String name;
    private String phoneNumber;
    private AddressVo addressVo;
    private Boolean usable;
    private int id;

    public static UserVo conversionUserVo(User user) {
        UserVo userVo = new UserVo();
        AddressVo addressVo = new AddressVo();
        userVo.setName(user.getName());
        userVo.setPhoneNumber(user.getPhoneNumber());
        userVo.setUsable(user.getUsable());
        userVo.setId(user.getId());
        addressVo.setProvince(user.getProvince());
        addressVo.setCity(user.getCity());
        addressVo.setArea(user.getArea());
        addressVo.setStreet(user.getStreet());
        addressVo.setVillage(user.getVillage());
        userVo.setAddressVo(addressVo);
        return userVo;
    }
    public static List<UserVo> conversionUserVo(List<User> users) {
        List<UserVo> userVos = new ArrayList<>();
        users.forEach(user ->
            userVos.add(conversionUserVo(user))
        );
        return userVos;
    }
}
