package com.work.recycle.repository;

import com.work.recycle.entity.CDOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CDOrderRepository extends BaseRepository<CDOrder,Integer> {

    @Query("select cd from CDOrder cd where cd.id = :id")
    CDOrder getCDOrderById(@Param("id") int id);
}
