package com.work.recycle.repository;

import com.work.recycle.entity.Garbage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarbageRepository extends BaseRepository<Garbage,Integer>{

    @Query("select g from Garbage g where 1 = 1")
    List<Garbage> getGarbage();

    @Query("select g from Garbage g where g.id = :id")
    Garbage getGarbageById(@Param("id") int id);

    @Query("select g from Garbage g where g.name = :name")
    Garbage getGarbageByName(@Param("name") String name);

}
