package com.work.recycle.repository;

import com.work.recycle.entity.CROrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CROrderRepository extends BaseRepository<CROrder,Integer> {

    @Query("select c from CROrder c where c.id = :id")
    CROrder getCROrderById(@Param("id") int id);

    @Query("select cr from CROrder cr where cr.recycleFirm.id = :id and cr.baseOrder.checkStatus = :checkStatus")
    List<CROrder> getCROrdersByRecycleFirm(@Param("id") int id,@Param("checkStatus") Boolean checkStatus);

    @Query("select cr from CROrder cr where cr.company = :company and cr.baseOrder.checkStatus = :checkStatus")
    List<CROrder> getCROrdersByCompany(@Param("checkStatus") Boolean checkStatus,@Param("company") String company);

    @Query("select cr from CROrder cr where cr.cleaner.id = :id and cr.baseOrder.checkStatus = :checkStatus")
    List<CROrder> getCROrdersByCleaner(@Param("id") int id ,@Param("checkStatus") Boolean checkStatus);
}
