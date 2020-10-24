package com.work.recycle.repository;

import com.work.recycle.entity.CDOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CDOrderRepository extends BaseRepository<CDOrder,Integer> {

    @Query("select cd from CDOrder cd where cd.id = :id")
    CDOrder getCDOrderById(@Param("id") int id);

    @Query("select cd from CDOrder cd where cd.driver.id = :id and cd.baseOrder.checkStatus = :status")
    List<CDOrder> getCDOrdersByDriverAndBaseOrder(@Param("id") int id,@Param("status") Boolean status);

    @Query("select cd from CDOrder cd where cd.cleaner.id = :id and cd.baseOrder.checkStatus = :status")
    List<CDOrder> getCDOrdersByCleanerAndBaseOrder(@Param("id") int id,@Param("status") Boolean status);

}
