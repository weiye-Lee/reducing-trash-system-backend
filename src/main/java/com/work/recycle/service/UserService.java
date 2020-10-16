package com.work.recycle.service;

import com.work.recycle.component.RequestComponent;
import com.work.recycle.controller.GarbageVo;
import com.work.recycle.entity.FCOrder;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.Goods;
import com.work.recycle.entity.User;
import com.work.recycle.repository.FCOrderRepository;
import com.work.recycle.repository.GarbageRepository;
import com.work.recycle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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


    public Map<String, List<Garbage>> constructMap(String categoryName, String[] typeName) {
        Map<String, List<Garbage>> map = new HashMap<>();
        for (String type : typeName) {
            String typeCHName = GarbageVo.typeCHName.get(type);
            String categoryCHName = GarbageVo.categoryCHName.get(categoryName);
            List<Garbage> garbageList = garbageRepository.getGarbageByType(typeCHName, categoryCHName);
            map.put(type, garbageList);
        }
        return map;

    }

    private Map<String, List<Garbage>> constructMap(String categoryName) {
        Map<String, List<Garbage>> map = new HashMap<>();
        String categoryCHName = GarbageVo.categoryCHName.get(categoryName);
        List<Garbage> garbageList = garbageRepository.getGarbageByType(categoryCHName);
        map.put(categoryName, garbageList);
        return map;
    }

    // TODO 2020/10/16 : 构造返回值
    public List<Map<String, Map<String, List<Garbage>>>> getGarbage() {
        Map<String, Map<String, List<Garbage>>> recycleMap = new HashMap<>();
        Map<String, Map<String, List<Garbage>>> unRecycleMap = new HashMap<>();
        Map<String, Map<String, List<Garbage>>> soilMap = new HashMap<>();

        recycleMap.put(
                GarbageVo.RECYCLE_CATEGORY,
                constructMap(GarbageVo.RECYCLE_CATEGORY, GarbageVo.recycleTypeName)
        );
        unRecycleMap.put(
                GarbageVo.UNRECYCLE_CATEGORY,
                constructMap(GarbageVo.UNRECYCLE_CATEGORY,GarbageVo.UnRecycleTypeName)
        );

        soilMap.put(
                GarbageVo.SOIL_CATEGORY,
                constructMap(GarbageVo.SOIL_CATEGORY)
        );
        List<Map<String, Map<String, List<Garbage>>>> list = new ArrayList<>();
        list.add(recycleMap);
        list.add(unRecycleMap);
        list.add(soilMap);
        return list;
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
