package com.work.recycle.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class User {

    public enum Sex {
        MALE, FEMALE
    }

    public enum Role{
        FARMER,CLEANER,DRIVER,RECYCLEFIRM,ADMIN,LENADER,TRANSFERSTATION
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 10)
    @Size(min = 2,max = 6,message = "用户名长度超出限制")
    private String name;

    @Column(length = 11,nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @Column(length = 20)
    @JsonIgnore
    private String IDNumber;

    @Column(length = 20)
    private String address;

    private Sex sex;

    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<GoodsExchange> goodsExchanges;

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
