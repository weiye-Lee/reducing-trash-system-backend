package com.work.recycle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class Cleaner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    @MapsId
    private User user;

    @Min(0)
    private double score = 0;

    // 用来存储
    private String otherVillage;

    @OneToMany(mappedBy = "cleaner")
    @JsonIgnore
    private List<Farmer> farmers;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Driver driver;

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

    public void addScore(double score) {
        this.score += score;
    }

    public void reduceScore(double score) {
        if ((this.score -= score) >= 0) {
            this.score -= score;
        }
    }

}
