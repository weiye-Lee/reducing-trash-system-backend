package com.work.recycle.repository;

import com.work.recycle.entity.RecycleFirm;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RecycleFirmRepository extends BaseRepository<RecycleFirm,Integer> {

    @Query("select f from RecycleFirm f where f.id = :id")
    RecycleFirm getRecycleFirmById(@Param("id") int id);


}
