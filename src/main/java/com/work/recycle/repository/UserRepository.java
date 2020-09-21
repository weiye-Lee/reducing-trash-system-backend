package com.work.recycle.repository;

import com.work.recycle.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User,Integer>  {

    @Query("select u.name,u.IDNumber,u.sex from User u where u.id = :id")
    User getUserById(@Param("id") int id);

    @Query("select u.phoneNumber from User u where u.phoneNumber = :phoneNumber")
    User getUserByPhoneNumber(@Param("phoneNumber") String phoneNumber);

}
