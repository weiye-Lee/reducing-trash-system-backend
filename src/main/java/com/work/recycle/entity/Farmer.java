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
    private Integer score = 0;

    @OneToMany(mappedBy = "farmer")
    @JsonIgnore
    private List<FarmerFamily> families;

    @OneToMany(mappedBy = "farmer")
    @JsonIgnore
    private List<FCOrder> fcOrders;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Cleaner cleaner;

    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

    @Column(columnDefinition = "timestamp default current_timestamp " +
            "on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;

    public void addScore(int score) {
        this.score += score;
    }

    public void reduceScore(int score) {
        if ((this.score -= score) >= 0) {
            this.score -= score;
        }
    }

}
