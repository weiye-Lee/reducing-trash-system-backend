package com.work.recycle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class Farmer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.PERSIST,CascadeType.REMOVE})
    @MapsId
    private User user;

    private String address;

    private Integer familySize = 1;

    @Min(0)
    private double score = 0;

    @OneToMany(mappedBy = "farmer")
    @JsonIgnore
    private List<FarmerFamily> families;

    @OneToMany(mappedBy = "farmer")
    @JsonIgnore
    private List<FCOrder> fcOrders;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cleaner cleaner;

    @JsonIgnore
    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

    @JsonIgnore
    @Column(columnDefinition = "timestamp default current_timestamp " +
            "on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;

    /**
     * 农户订单完成增加积分
     * @param score 要增加的积分数
     */
    public void addScore(double score) {
        this.score += score;
    }

    /**
     * 农户进行商品交换时扣除积分
     * @param score 要扣除的积分
     */
    public void reduceScore(double score) {
        if ((this.score -= score) >= 0) {
            this.score -= score;
        }
    }

}
