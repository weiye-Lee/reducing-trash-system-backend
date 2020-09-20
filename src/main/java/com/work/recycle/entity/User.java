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
    @Id
    // 自增，可以通过uuid对其进行处理
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "user")
    private List<UserRole> roleList;

    @Column(length = 10)
    @Size(min = 2,max = 6,message = "用户名长度超出限制")
    private String name;

    @Column(length = 20,nullable = false)
    private String phoneNumber;

    @Column(length = 20)
    private String IDNumber;

    @Min(0)
    private Integer score = 0;
    @Column(length = 10)
    private String sex;

    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

    @OneToMany(mappedBy = "user")
    private List<GoodsExchange> goodsExchanges;
}
