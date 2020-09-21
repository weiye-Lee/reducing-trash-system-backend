package com.work.recycle.service;

import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.Goods;
import com.work.recycle.entity.User;
import com.work.recycle.repository.GarbageRepository;
import com.work.recycle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GarbageRepository garbageRepository;

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public List<Garbage> getGarbage() {
        return garbageRepository.getGarbage();
    }

    public User getUserByPhone(String phoneNumber) {
        return userRepository.getUserByPhoneNumber(phoneNumber);
    }



}
