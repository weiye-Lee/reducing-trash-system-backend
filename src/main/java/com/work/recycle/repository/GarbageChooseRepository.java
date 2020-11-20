package com.work.recycle.repository;

import com.work.recycle.entity.GarbageChoose;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GarbageChooseRepository extends BaseRepository<GarbageChoose,Integer> {

    @Query("select g from GarbageChoose g where g.id = :id")
    GarbageChoose getGarbageChooseById(@Param("id") int id);

    @Query("select g from GarbageChoose g where g.baseOrder.id = :id")
    List<GarbageChoose> getGarbageChooseByBaseOrder_Id(@Param("id") int id);
}
