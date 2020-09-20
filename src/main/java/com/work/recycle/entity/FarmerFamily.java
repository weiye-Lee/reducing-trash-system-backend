package com.work.recycle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FarmerFamily {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    @Size(min = 18,max = 18)
    private String IdNumber;

    @Column(length = 20)
    @Size(min = 11,max = 11)
    private String phoneNumber;

    // 农户家庭中户主id
    @ManyToOne
    private Farmer farmer;

}
