package com.work.recycle.repository;

import com.work.recycle.entity.RecycleFirmCompany;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RecycleFirmCompanyRepo extends BaseRepository<RecycleFirmCompany,Integer> {
    @Query("select r from RecycleFirmCompany r where r.id = :id")
    RecycleFirmCompany getById(@Param("id") int id);
}
