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
public class Cleaner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    @MapsId
    private User user;

    @Min(0)
    private Integer score = 0;

    private String serviceArea;

    @OneToMany(mappedBy = "cleaner")
    private List<Farmer> farmers;

    @OneToMany(mappedBy = "cleaner")
    private List<FCOrder> fcOrders;

    @OneToMany(mappedBy = "cleaner")
    private List<CDOrder> cdOrders;

    @OneToMany(mappedBy = "cleaner")
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
