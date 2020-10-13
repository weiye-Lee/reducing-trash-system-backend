package com.work.recycle.repository;

import com.work.recycle.entity.CROrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CROrderRepository extends BaseRepository<CROrder,Integer> {

    @Query("select c from CROrder c where c.id = :id")
    CROrder getCROrderById(@Param("id") int id);
}
