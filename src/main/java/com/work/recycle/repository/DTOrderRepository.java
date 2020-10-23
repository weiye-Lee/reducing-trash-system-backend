package com.work.recycle.repository;

import com.work.recycle.entity.DTOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DTOrderRepository extends BaseRepository<DTOrder,Integer> {
    @Query("select dt from DTOrder dt where dt.transferStation.id = :id")
    List<DTOrder> getDTOrdersByTransferStation(@Param("id") int id);
}
