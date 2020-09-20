package com.work.recycle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class RecycleFirm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    @MapsId
    private User user;

    @Column(length = 40)
    private String serviceArea;

    @OneToMany(mappedBy = "recycleFirm")
    private List<CROrder> crOrders;

    // @Column(columnDefinition = "timestamp default current_timestamp",
    //         insertable = false,
    //         updatable = false)
    // private LocalDateTime insertTime;
    //
    // @Column(columnDefinition = "timestamp default current_timestamp " +
    //         "on update current_timestamp",
    //         insertable = false,
    //         updatable = false)
    // private LocalDateTime updateTime;
}
