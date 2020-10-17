package com.work.recycle.controller.vo;

import com.work.recycle.entity.GarbageChoose;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Iterator;
import java.util.List;

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
