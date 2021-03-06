package com.work.recycle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 回收企业员工
 */
@Entity
@Data
@NoArgsConstructor
public class RecycleFirm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.REMOVE, CascadeType.PERSIST})
    @MapsId
    private User user;

    @Column(length = 40)
    private String serviceArea;

    private String company;

    private String sector;

    @OneToMany(mappedBy = "recycleFirm")
    @JsonIgnore
    private List<CROrder> crOrders;

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
