package com.work.recycle.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer goodsId;

    @Min(1)
    private Integer integral = 99999; // 积分

    @Column(length = 40,unique = true)
    @NotNull
    private String name;

    // 用于判断是否展示
    @Column(length = 10)
    private Boolean ifShow = true;

    private String photoUrl;

    @Column(length = 40)
    private String goodsDesc; // 商品描述

    @Min(0)
    private int price = 100; // 商品价格

    @OneToMany(mappedBy = "goods",cascade = CascadeType.REMOVE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<GoodsExchange> goodsExchanges;


    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

    @Column(columnDefinition = "timestamp default current_timestamp " +
            "on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;
}