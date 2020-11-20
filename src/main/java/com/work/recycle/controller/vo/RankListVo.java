package com.work.recycle.controller.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class RankListVo {
    private String name;
    private double score;
    private Integer id;
    private AddressVo addressVo;

    public RankListVo(String name,double score,Integer id) {
        this.name = name;
        this.score = score;
        this.id = id;

    }

}
