package com.work.recycle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data

public class Garbage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10,nullable = false,unique = true)
    private String name;

    @Column(length = 40)
    private String category; // 种类

    private Integer score = 0; // 积分

    @Column(length = 10)
    private String unit; // 计量单位

    @Min(0)
    private Integer amount = 0; // 数量

    private String picture; // 垃圾的图片

    @ManyToOne
    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private FCOrder fcOrder;

    @ManyToOne
    @JsonIgnore
    private CDOrder cdOrder;

    @ManyToOne
    @JsonIgnore
    private CROrder crOrder;

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
