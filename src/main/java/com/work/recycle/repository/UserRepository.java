package com.work.recycle.repository;

import com.work.recycle.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends BaseRepository<User,Integer>  {
}
