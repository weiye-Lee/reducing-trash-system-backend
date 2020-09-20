package com.work.recycle.repository;

import com.work.recycle.entity.Goods;
import org.springframework.stereotype.Repository;

@Repository
public interface GoodsRepository extends BaseRepository<Goods,Integer> {
}
