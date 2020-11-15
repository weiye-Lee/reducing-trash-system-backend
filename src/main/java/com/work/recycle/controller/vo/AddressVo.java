package com.work.recycle.controller.vo;

import lombok.Data;

@Data
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
}
