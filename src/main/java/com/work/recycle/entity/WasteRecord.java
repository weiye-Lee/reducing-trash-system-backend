package com.work.recycle.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 危废垃圾记录表
 */
@Data
@Entity
public class WasteRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Cleaner cleaner;

    private double total; // 保洁员提交危废垃圾数量

    private String unit; //  危废垃圾单位

    private String wasteCompany; // 处理危废垃圾公司

    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;
}
