package com.work.recycle.service;

import com.work.recycle.component.RequestComponent;
import com.work.recycle.controller.vo.GarbageVo;
import com.work.recycle.entity.FCOrder;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.User;
import com.work.recycle.repository.FCOrderRepository;
import com.work.recycle.repository.GarbageRepository;
import com.work.recycle.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    /**
     * 构造垃圾返回列表的键值对
     * @param categoryName ：GarbageVo.categoryName 垃圾大类（e.g. 可回收垃圾，不可回收垃圾...）
     * @param typeName : GarbageVo.typeName 垃圾类别（e.g. 可回收垃圾中纸张类、玻璃类）
     * @return Map < 垃圾类别, List< 对应垃圾类别的 垃圾 > >
     *     // e.g
     *     "metal": [
     *           {
     *             "id": 12,
     *             "name": "废电池",
     *             "category": "不可回收垃圾",
     *             "type": "金属类",
     *             "score": 1.0,
     *             "unit": "节"
     *           },
     *           {
     *             "id": 13,
     *             "name": "废灯管 ",
     *             "category": "不可回收垃圾",
     *             "type": "金属类",
     *             "score": 1.0,
     *             "unit": "个"
     *           }
     *         ]
     */
    private Map<String, List<Garbage>> constructMap(String categoryName, String[] typeName) {
        Map<String, List<Garbage>> map = new HashMap<>();
        for (String type : typeName) {
            String typeCHName = GarbageVo.typeCHName.get(type);
            String categoryCHName = GarbageVo.categoryCHName.get(categoryName);
            List<Garbage> garbageList = garbageRepository.getGarbageByType(typeCHName, categoryCHName);
            map.put(type, garbageList);
        }
        return map;

    }

    /**
     * 根据垃圾大类进行查询获取 ，由于某些垃圾只有大类，没有类别（type）（e.g 煤渣）
     * @param categoryName GarbageVo.categoryName 垃圾种类(e.g 煤渣、废土)
     * @return Map < 垃圾类别, List< 对应垃圾类别的 垃圾 > >
     *     // e.g
     *     {
     *       "soil": {
     *         "soil": [
     *           {
     *             "id": 17,
     *             "name": "煤渣、灰土",
     *             "category": "煤渣、灰土",
     *             "type": "煤渣、灰土",
     *             "score": 0.1,
     *             "unit": "斤"
     *           }
     *         ]
     *       }
     *     }
     */
    private Map<String, List<Garbage>> constructMap(String categoryName) {
        Map<String, List<Garbage>> map = new HashMap<>();
        String categoryCHName = GarbageVo.categoryCHName.get(categoryName);
        List<Garbage> garbageList = garbageRepository.getGarbageByType(categoryCHName);
        map.put(categoryName, garbageList);
        return map;
    }

    public Map<String, Map<String, List<Garbage>>> getRecycleGarbage() {
        Map<String, Map<String, List<Garbage>>> recycleMap = new HashMap<>();

        recycleMap.put(
                GarbageVo.RECYCLE_CATEGORY,
                constructMap(GarbageVo.RECYCLE_CATEGORY, GarbageVo.recycleTypeName)
        );
        return recycleMap;
    }

    public Map<String, Map<String, List<Garbage>>> getUnRecycleGarbage() {
        Map<String, Map<String, List<Garbage>>> unRecycleMap = new HashMap<>();

        unRecycleMap.put(
                GarbageVo.UNRECYCLE_CATEGORY,
                constructMap(GarbageVo.UNRECYCLE_CATEGORY,GarbageVo.UnRecycleTypeName)
        );
        return unRecycleMap;
    }

    public Map<String, Map<String, List<Garbage>>> getSoilGarbage() {
        Map<String, Map<String, List<Garbage>>> soilMap = new HashMap<>();
        soilMap.put(
                GarbageVo.SOIL_CATEGORY,
                constructMap(GarbageVo.SOIL_CATEGORY)
        );
        return soilMap;
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
