package com.work.recycle.repository;

import com.work.recycle.entity.Farmer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface FarmerRepository extends BaseRepository<Farmer,Integer> {

    @Query("select f.score from Farmer f where f.id = :id")
    int getScoreById(@Param("id") Integer id);

    @Query("select f from Farmer f where f.id = :id")
    Farmer getFarmerById(@Param("id") int id);

    @Query("select f from Farmer f where f.user.phoneNumber = :phoneNumber")
    Farmer getFarmerByPhoneNumber(@Param("phoneNumber") String PhoneNumber);

}
