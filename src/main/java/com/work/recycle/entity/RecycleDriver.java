package com.work.recycle.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * 可回收司机
 */
@Data
@Entity
public class RecycleDriver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String name;

    private String phoneNumber;

    private String sex = "男";

    private String address; // 类似于服务位置信息

    @OneToMany(mappedBy = "recycleDriver")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<CROrder> crOrderList;
}
