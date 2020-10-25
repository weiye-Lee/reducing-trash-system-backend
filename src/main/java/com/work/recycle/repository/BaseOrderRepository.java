package com.work.recycle.repository;

import com.work.recycle.entity.BaseOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseOrderRepository extends BaseRepository<BaseOrder,Integer> {
    @Query("select o from BaseOrder o where o.id = :id")
    BaseOrder getBaseOrderById(@Param("id") int id);
}
