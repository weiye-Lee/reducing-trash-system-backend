package com.work.recycle.entity;

import jdk.jfr.Enabled;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
}
