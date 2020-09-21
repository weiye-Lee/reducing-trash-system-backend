package com.work.recycle.repository;

import com.work.recycle.entity.Garbage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GarbageRepository extends BaseRepository<Garbage,Integer>{

    @Query("select g.category,g.name,g.picture,g.score,g.unit from Garbage g where 1 = 1")
    List<Garbage> getGarbage();

}
