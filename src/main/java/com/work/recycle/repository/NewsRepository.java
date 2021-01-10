package com.work.recycle.repository;

import com.work.recycle.entity.News;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface NewsRepository extends BaseRepository<News,Integer>{
//    @Query("select n from News n where n.farmerShow = true order by n.level limit :limit")
//    List<News> getbyFarmerShow(@Param("limit") int limit);

//    List<News> findByFarmerShowLIMT();
}
