package com.work.recycle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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
    private String category; // 种类（可回收垃圾）
    
    @Column(length = 40)
    private String type; // 种类 （不可回收垃圾）

    private Integer score = 0; // 积分

    @Column(length = 10)
    private String unit; // 计量单位

    @OneToMany(mappedBy = "garbage",cascade = CascadeType.PERSIST)
    @JsonIgnore
    private List<GarbageChoose> garbageChooseList;

    private String picture; // 垃圾的图片

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
