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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<UserRole> roleList;

    @Column(length = 10)
    @Size(min = 2,max = 6,message = "用户名长度超出限制")
    private String name;

    @Column(length = 11,nullable = false)
    private String phoneNumber;

    @Column(length = 20)
    private String IDNumber;

    private Sex sex;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<GoodsExchange> goodsExchanges;

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
