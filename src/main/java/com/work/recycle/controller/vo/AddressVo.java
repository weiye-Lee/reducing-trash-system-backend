package com.work.recycle.controller.vo;

import com.work.recycle.common.ResultCode;
import com.work.recycle.entity.User;
import com.work.recycle.exception.ApiException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddressVo {
    public static final String PROVINCE = "province";
    public static final String CITY = "city";
    public static final String AREA = "area";
    public static final String STREET = "street";
    public static final String VILLAGE = "village";
    public static final String NONE = "none";
    private String province;
    private String city;
    private String area;
    private String street;
    private String village;

    public static AddressVo getAddressVo(User user) {
        if (user == null ) {
            throw new ApiException(ResultCode.FAILED);
        }
        return new AddressVo(
                user.getProvince()
                ,user.getCity()
                ,user.getArea()
                ,user.getStreet()
                ,user.getVillage()
        );
    }
}
