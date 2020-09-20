package com.work.recycle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CDOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Cleaner cleaner;

    @ManyToOne
    private Driver driver;

    // cleaner 和 driver 之间都是不可回收垃圾，应该没有积分吧
    // private Integer score = 0;

    @OneToMany(mappedBy = "cdOrder")
    private List<Garbage> garbageList;

    private Boolean status;

    @ManyToOne
    private DTOrder dtOrder;

    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;
}
