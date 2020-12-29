package com.work.recycle.entity;

import lombok.Data;

import javax.persistence.*;
@Entity
@Data
public class SuggestPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne
    private SuggestPriceRecord suggestPriceRecord;

    @OneToOne
    private Garbage garbage;

    private Double price;

    private String unit;
}
