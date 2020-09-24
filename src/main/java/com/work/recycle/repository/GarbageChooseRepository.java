package com.work.recycle.repository;

import com.work.recycle.entity.GarbageChoose;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GarbageChooseRepository extends BaseRepository<GarbageChoose,Integer> {

    @Query("select g from GarbageChoose g where g.id = :id")
    GarbageChoose getGarbageChooseById(@Param("id") int id);
}
