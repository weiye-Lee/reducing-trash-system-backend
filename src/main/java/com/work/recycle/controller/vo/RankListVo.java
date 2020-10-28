package com.work.recycle.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankListVo {
    private String name;
    private String address;
    private double score;
    private Integer id;

    public RankListVo(String name,double score,Integer id) {
        this.name = name;
        this.score = score;
        this.id = id;

    }
}
