package com.work.recycle.controller.vo;

import com.work.recycle.entity.News;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class NewsVo {
    private int id;
    private String title;
    private LocalDateTime time;


    public static NewsVo constructVo(News news) {
        if (news == null) {
            return null;
        }
        NewsVo newsVo = new NewsVo();
        newsVo.setTime(news.getInsertTime());
        newsVo.setTitle(news.getTitle());
        newsVo.setId(news.getId());
        return newsVo;
    }
}
