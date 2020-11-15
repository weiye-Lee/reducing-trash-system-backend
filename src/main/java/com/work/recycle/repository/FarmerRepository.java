package com.work.recycle.repository;

import com.work.recycle.entity.Cleaner;
import com.work.recycle.entity.Farmer;
import com.work.recycle.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerRepository extends BaseRepository<Farmer,Integer> {

    @Query("select f.score from Farmer f where f.id = :id")
    int getScoreById(@Param("id") Integer id);

    @Query("select f from Farmer f where f.id = :id")
    Farmer getFarmerById(@Param("id") int id);

    @Query("select f from Farmer f where f.user.phoneNumber = :phoneNumber")
    Farmer getFarmerByPhoneNumber(@Param("phoneNumber") String PhoneNumber);


    List<Farmer> findTop10ByOrderByScoreDesc();

    List<Farmer> findTop10ByUser_ProvinceOrderByScoreDesc(String province);

    List<Farmer> findTop10ByUser_CityOrderByScoreDesc(String city);

    List<Farmer> findTop10ByUser_AreaOrderByScoreDesc(String area);

    List<Farmer> findTop10ByUser_StreetOrderByScoreDesc(String street);

    List<Farmer> findTop10ByUser_VillageOrderByScoreDesc(String village);
}
