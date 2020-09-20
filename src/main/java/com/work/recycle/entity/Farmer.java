package com.work.recycle.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "farmer")
    private List<FarmerFamily> families;

    @OneToMany(mappedBy = "farmer")
    private List<FCOrder> fcOrders;

    @ManyToOne
    private Cleaner cleaner;

}
