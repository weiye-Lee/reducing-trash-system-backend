package com.work.recycle.service;

import com.work.recycle.entity.User;
import com.work.recycle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Optional<User> getUserById(int id) {
        return userRepository.findById(id);
    }

    public User getUserByPhone(String phone) {
        return userRepository.getUserByPhoneNumber(phone);
    }
}
