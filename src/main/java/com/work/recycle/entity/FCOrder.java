package com.work.recycle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FCOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 40)
    private String address;

    @Column(nullable = false)
    @Min(0)
    private Integer score = 0; // 一条提交记录的中产生的积分

    @Column(length = 10)
    private Boolean status = false; // 接单状态

    @Column(length = 40)
    private String remark;

    @ManyToOne
    private Farmer farmer;

    @ManyToOne
    private Cleaner cleaner;

    @OneToMany(mappedBy = "fcOrder")
    private List<Garbage> garbageList;

    // farmer 发布订单时间
    @Column(columnDefinition = "timestamp default current_timestamp", insertable = false,updatable = false)
    private LocalDateTime farmerTime;

    // cleaner 完成订单时间
    @Column(columnDefinition = "timestamp default current_timestamp", insertable = false)
    private LocalDateTime cleanerTime;

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
