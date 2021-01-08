package com.work.recycle.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 标准订单列表返回Vo
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexOrderVo {
    private String msg;
    private Integer id;
    private LocalDateTime date;
    private Boolean status;
    private String address;
    private String province;
    private String city;
    private String area;
    private String street;
    private String village;
    private Double tradePrice;
}
