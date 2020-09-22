package com.work.recycle.entity;

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
    // 自增，可以通过uuid对其进行处理
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    //只能反序列化为java，不能序列化为json。也就是json返回不到前端
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
