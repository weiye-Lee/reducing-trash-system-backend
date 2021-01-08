package com.work.recycle.repository;

import com.work.recycle.entity.RecycleDriver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecycleDriverRepository extends BaseRepository<RecycleDriver,Integer> {
    @Query("select r from RecycleDriver r where r.id = :id")
    RecycleDriver getById(@Param("id") int id);

}
