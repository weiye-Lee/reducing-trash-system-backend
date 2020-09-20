package com.work.recycle.entity;

import javax.persistence.*;
import javax.validation.constraints.Min;

@Entity
public class Garbage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10,nullable = false,unique = true)
    private String name;

    @Column(length = 40)
    private String category; // 种类

    private Integer score = 0; // 积分

    @Column(length = 10)
    private String unit; // 计量单位

    @Min(0)
    private Integer amount = 0; // 数量

    private String priture;

    @ManyToOne
    private FCOrder fcOrder;

    @ManyToOne
    private CDOrder cdOrder;

    @ManyToOne
    private CROrder crOrder;
}
