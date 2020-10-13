package com.work.recycle.repository;

import com.work.recycle.entity.Driver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends BaseRepository<Driver,Integer> {

    @Query("select d from Driver d where d.id = :id")
    Driver getDriverById(@Param("id") int id);
}
