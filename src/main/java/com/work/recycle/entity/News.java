package com.work.recycle.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 公告新闻
 */
@Data
@Entity

public class News {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 15)
    private String title;

    private String author; // 作者

    private String content;

    @Column(columnDefinition = "timestamp default current_timestamp",
            insertable = false,
            updatable = false)
    private LocalDateTime insertTime;

    private int level;

    private Boolean hidden = false; // 是否显示

    /* 该新闻对哪些用户显示*/
    private Boolean farmerShow = false;

    private Boolean cleanerShow = false;

    private Boolean kitchenShow = false;

    private Boolean recycleFirmShow = false;

    private Boolean transferStationShow = false;
}
