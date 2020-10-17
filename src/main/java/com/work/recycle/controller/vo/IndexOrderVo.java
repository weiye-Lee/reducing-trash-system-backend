package com.work.recycle.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 订单列表vo
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

}
