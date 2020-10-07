package com.work.recycle.repository;

import com.work.recycle.entity.TransferStation;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransferStationRepository extends BaseRepository<TransferStation,Integer> {

    @Query("select t from TransferStation t where t.id = :id")
    TransferStation getTransferStationById(@Param("id") int id);
}
