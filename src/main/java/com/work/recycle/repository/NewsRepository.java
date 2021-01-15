package com.work.recycle.repository;

import com.work.recycle.entity.News;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface NewsRepository extends BaseRepository<News,Integer>{
    @Query("select n from News n where n.farmerShow = true")
    List<News> getbyFarmerShow();

    @Query("select n from News n where n.cleanerShow = true")
    List<News> getbyCleanerShow();

    @Query("select n from News n where n.kitchenShow = true")
    List<News> getbyKitchenShow();

    @Query("select n from News n where n.recycleFirmShow = true")
    List<News> getByRecycleFirmShow();

    @Query("select n from News n where n.transferStationShow = true")
    List<News> getByTransferStationShow();

}
