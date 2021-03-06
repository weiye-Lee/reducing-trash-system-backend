package com.work.recycle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CROrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @MapsId
    private BaseOrder baseOrder;

    @ManyToOne
    private Cleaner cleaner;

    @ManyToOne
    private RecycleFirm recycleFirm;

    private double tradePrice;

    @ManyToOne
    private RecycleDriver recycleDriver;

    private String company; // 回收公司

    private String sector; // 公司部门

}
