package com.work.recycle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
@Data
@Entity
public class RecyclePriceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany(mappedBy = "recyclePriceRecord")
    private List<RecyclePrice> recyclePriceLists;

    @JsonIgnore
    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime time;
}
