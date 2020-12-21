package com.work.recycle.repository;

import com.work.recycle.entity.BaseOrder;
import com.work.recycle.entity.FCOrder;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FCOrderRepository extends BaseRepository<FCOrder, Integer> {

    @Query("select count(fc) from FCOrder fc where fc.farmer.id = :id")
    int getFarmerFCOrderTimesById(@Param("id") int id);

    @Query("select count (fc) from FCOrder fc where fc.cleaner.id = :id and fc.baseOrder.checkStatus = true")
    int getCleanerFCOrderTimesById(@Param("id") int id);

    @Query("select fc from FCOrder fc where fc.farmer.id = :id")
    List<FCOrder> getFarmerFCOrdersById(@Param("id") int id);

    @Query("select fc from FCOrder fc where fc.farmer.id = :id and fc.baseOrder.checkStatus = :checkStatus")
    List<FCOrder> getFarmerFCOrdersById(@Param("id") int id, @Param("checkStatus") Boolean checkStatus);

    @Query("select fc from FCOrder fc where fc.cleaner.id = :id")
    List<FCOrder> getCleanerFCOrdersById(@Param("id") int id);

    @Query("select fc from FCOrder fc where fc.cleaner.id = :id and fc.baseOrder.checkStatus = :checkStatus")
    List<FCOrder> getCleanerFCOrdersById(@Param("id") int id, @Param("checkStatus") Boolean checkStatus);


    @Query("select fc from FCOrder fc where fc.id = :id")
    FCOrder getFCOrderById(@Param("id") int id);

    @Query("select fc from FCOrder fc where " +
            " fc.baseOrder.street = :street" +
            " and fc.baseOrder.insertTime >= :startTime" +
            " and fc.baseOrder.insertTime <= :endTime")
    List<FCOrder> getByAddressAndTime(@Param("street") String street,
                                      @Param("startTime")LocalDateTime startTime,
                                      @Param("endTime")LocalDateTime endTime);

}
