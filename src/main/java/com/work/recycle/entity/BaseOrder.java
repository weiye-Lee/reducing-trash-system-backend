package com.work.recycle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class BaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double score = 0;

    @JsonIgnore
    private Boolean receiveStatus = false;

    private Boolean checkStatus = false;

    @Column(length = 40)
    private String address;

    @Column(length = 40)
    private String remark;

    @Column(length = 10)
    private String name;

    @Column(length = 12)
    private String phoneNumber;

    // @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "baseOrder",cascade = CascadeType.PERSIST)
    private List<GarbageChoose> garbageChooses;

    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

    @Column(columnDefinition = "timestamp default current_timestamp " +
            "on update current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime updateTime;

    @Column(length = 10)
    private String province;

    @Column(length = 10)
    private String city;

    @Column(length = 10)
    private String area;

    @Column(length = 10)
    private String street;

    @Column(length = 20)
    private String village;
}
