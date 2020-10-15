package com.work.recycle.service;

import com.work.recycle.component.RequestComponent;
import com.work.recycle.entity.FCOrder;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.Goods;
import com.work.recycle.entity.User;
import com.work.recycle.repository.FCOrderRepository;
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
    @Autowired
    private FCOrderRepository fcOrderRepository;
    @Autowired
    private RequestComponent requestComponent;

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public User getUserByPhone(String phone) {
        return userRepository.getUserByPhoneNumber(phone);
    }



    public List<Garbage> getGarbage() {
        return garbageRepository.getGarbage();
    }

    public Garbage getGarbageById(int id) {

        return garbageRepository.getGarbageById(id);
    }

    public User updateUserInfo(User userInfo) {
        User user = userRepository.getUserById(userInfo.getId());
        String name = userInfo.getName();
        User.Sex sex = userInfo.getSex();
        user.setName(name);
        user.setSex(sex);
        userRepository.save(user);
        return user;
    }

    public List<FCOrder> getFCOrders() {
        int uid = requestComponent.getUid();
        return fcOrderRepository.getFarmerFCOrdersById(uid);

    }

    public Garbage getGarbage(String name) {
        return garbageRepository.getGarbageByName(name);
    }
}
