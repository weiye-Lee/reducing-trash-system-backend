package com.work.recycle.repository;


import com.work.recycle.entity.Cleaner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CleanerRepository extends BaseRepository<Cleaner,Integer>{

    @Query("select c from Cleaner c where c.id = :id")
    Cleaner getCleanerById(@Param("id") int id);

    @Query("select c.score from Cleaner c where c.id = :id")
    int getScoreById(@Param("id") int id);

    @Query("select c from Cleaner c where c.user.phoneNumber = :phoneNumber")
    Cleaner getCleanerByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}
