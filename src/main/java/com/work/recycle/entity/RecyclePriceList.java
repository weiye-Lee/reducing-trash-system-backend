package com.work.recycle.entity;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
public class RecyclePriceList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private RecyclePriceRecord recyclePriceRecord;

    @OneToOne
    private Garbage garbage;

    private Double price;

    private String unit;
}
