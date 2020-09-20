package com.work.recycle.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
@Entity
@Data
@NoArgsConstructor
public class GoodsExchange {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer GoodsExchange_id;

    @Column(columnDefinition = "timestamp default current_timestamp")
    private LocalDateTime exchange_time;

    @ManyToOne
    private Goods goods;

    @ManyToOne
    private User user;

}
