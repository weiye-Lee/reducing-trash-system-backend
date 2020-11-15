package com.work.recycle.repository;


import com.work.recycle.entity.Cleaner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CleanerRepository extends BaseRepository<Cleaner,Integer>{

    @Query("select c from Cleaner c where c.id = :id")
    Cleaner getCleanerById(@Param("id") int id);

    @Query("select c.score from Cleaner c where c.id = :id")
    int getScoreById(@Param("id") int id);

    @Query("select c from Cleaner c where c.user.phoneNumber = :phoneNumber")
    Cleaner getCleanerByPhoneNumber(@Param("phoneNumber") String phoneNumber);

    List<Cleaner> findTop10ByOrderByScoreDesc();

    List<Cleaner> findTop10ByUser_ProvinceOrderByScoreDesc(String province);

    List<Cleaner> findTop10ByUser_CityOrderByScoreDesc(String city);

    List<Cleaner> findTop10ByUser_AreaOrderByScoreDesc(String area);

    List<Cleaner> findTop10ByUser_StreetOrderByScoreDesc(String street);

    List<Cleaner> findTop10ByUser_VillageOrderByScoreDesc(String village);

}
