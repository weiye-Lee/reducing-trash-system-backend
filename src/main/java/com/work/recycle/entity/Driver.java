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
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(cascade = {CascadeType.REMOVE,CascadeType.PERSIST})
    @MapsId
    private User user;

    @OneToMany(mappedBy = "driver")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Cleaner> cleaners;

    @Column(length = 40)
    private String serviceArea;

    @OneToMany(mappedBy = "driver")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<CDOrder> cdOrders;

    @OneToMany(mappedBy = "driver")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<DTOrder> dtOrders;

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
}
