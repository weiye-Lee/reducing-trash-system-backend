package com.work.recycle.controller.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;


/**
 * 订单列表筛选控制类
 * startTime endTime 按照时间查询
 * addressVo 按照地理位置查询 (province , city , etc ..)
 * col 查询控制因子 用来标识通过什么条件进行查询
 *          t 时间(time)
 *          a 位置(address)
 *          t_a 时间和位置(time and address)
 */
@Data
public class SiftOrderVo {
    /**
     * 查询控制条件
     * T time
     * I id
     * ====== > but i don't want to realize the function of select by id < ======
     * A Address
     * 例 T_I : select by time and id
     * 例 T_I_A : select by time and id and address
     * etc ..
     */
    public static final String T = "t";
    public static final String T_A = "t_a";
    public static final String A = "a";
    // public static final String T_I = "t_i";
    // public static final String T_I_A = "t_i_a";
    // public static final String I = "i";
    // public static final String I_A = "i_a";

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @JsonFormat( pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    private AddressVo addressVo;

    @NotNull
    private String col;

}
