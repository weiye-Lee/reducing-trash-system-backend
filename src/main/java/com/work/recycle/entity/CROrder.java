package com.work.recycle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class CROrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Cleaner cleaner;

    @ManyToOne
    private RecycleFirm recycleFirm;

    private Integer score;

    @OneToMany(mappedBy = "crOrder")
    private List<Garbage> garbageList;

    private Boolean status;

}
