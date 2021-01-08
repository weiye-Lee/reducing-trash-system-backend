package com.work.recycle.component;


import com.work.recycle.entity.*;
import com.work.recycle.repository.*;
import com.work.recycle.service.FarmerService;
import com.work.recycle.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class InitComponent implements InitializingBean {
    @Autowired
    private RecycleDriverRepository recycleDriverRepository;
    @Autowired
    private TransferStationRepository transferStationRepository;
    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private GarbageRepository garbageRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private RecycleFirmRepository recycleFirmRepository;

    @Override
    public void afterPropertiesSet() throws Exception {

        User u = userService.getUserByPhone("13050496540");
        if (u == null) {
            User user3 = new User();
            user3.setName("司机A");
            user3.setAddress("村口第一家");
            user3.setProvince("黑龙江省");
            user3.setCity("哈尔滨市");
            user3.setArea("双城区");
            user3.setStreet("承恩街道");
            user3.setVillage("永和村");
            user3.setSex(User.Sex.MALE);
            user3.setRole(User.Role.DRIVER);
            user3.setPhoneNumber("13050496540");
            user3.setPassword(encoder.encode("123456"));
            Driver driver = new Driver();
            driver.setUser(user3);
            driverRepository.save(driver);
        }


        u = userService.getUserByPhone("13050496541");
        if (u == null) {
            User user2 = new User();
            user2.setName("保洁员A");
            user2.setAddress("村口第一家");
            user2.setProvince("黑龙江省");
            user2.setCity("哈尔滨市");
            user2.setArea("双城区");
            user2.setStreet("承恩街道");
            user2.setVillage("永和村");
            user2.setSex(User.Sex.MALE);
            user2.setRole(User.Role.CLEANER);
            user2.setPhoneNumber("13050496541");
            user2.setPassword(encoder.encode("123456"));
            Cleaner cleaner = new Cleaner();
            cleaner.setUser(user2);
            cleaner.setDriver(driverRepository.getDriverByPhoneNumber("13050496540"));
            cleanerRepository.save(cleaner);
        }

        u = userService.getUserByPhone("13050496542");
        if (u == null) {
            User user = new User();
            user.setProvince("黑龙江省");
            user.setCity("哈尔滨市");
            user.setArea("双城区");
            user.setStreet("承恩街道");
            user.setVillage("永和村");
            user.setName("农户姓名");
            user.setAddress("村口第一家");
            user.setSex(User.Sex.MALE);
            user.setRole(User.Role.FARMER);
            user.setPhoneNumber("13050496542");
            user.setPassword(encoder.encode("123456"));
            Farmer farmer = new Farmer();
            farmer.setUser(user);
            farmer.setCleaner(cleanerRepository.getCleanerByPhoneNumber("13050496541"));
            farmerRepository.save(farmer);
        }

        u = userService.getUserByPhone("13050496543");
        if (u == null) {
            User user = new User();
            user.setName("中转站姓名");
            user.setProvince("黑龙江省");
            user.setCity("哈尔滨市");
            user.setArea("双城区");
            user.setStreet("承恩街道");
            user.setVillage("永和村");
            user.setRole(User.Role.TRANSFERSTATION);
            user.setPhoneNumber("13050496543");
            user.setPassword(encoder.encode("123456"));
            TransferStation transferStation = new TransferStation();
            transferStation.setUser(user);
            transferStationRepository.save(transferStation);
        }

        u = userService.getUserByPhone("13050496544");
        if (u == null) {
            User user = new User();
            user.setName("回收企业姓名");
            user.setAddress("村口第一家");
            user.setProvince("黑龙江省");
            user.setCity("哈尔滨市");
            user.setArea("双城区");
            user.setStreet("承恩街道");
            user.setVillage("永和村");
            user.setRole(User.Role.RECYCLEFIRM);
            user.setPhoneNumber("13050496544");
            user.setPassword(encoder.encode("123456"));
            RecycleFirm recycleFirm = new RecycleFirm();
            recycleFirm.setUser(user);
            recycleFirmRepository.save(recycleFirm);
        }

        u = userService.getUserByPhone("13050406666");
        if (u == null) {
            User user = new User();
            user.setName("王根源");
            user.setAddress("村口第一家");
            user.setProvince("黑龙江省");
            user.setCity("哈尔滨市");
            user.setArea("双城区");
            user.setStreet("承恩街道");
            user.setVillage("永和村");
            user.setRole(User.Role.ADMIN);
            user.setPhoneNumber("13050406666");
            user.setPassword(encoder.encode("123456"));
            userRepository.save(user);
        }


        if (garbageRepository.getGarbageById(1) == null) {
            String RecycleCategory = "可回收垃圾";
            String UnRecycleCategory = "不可回收垃圾";
            String type = "纸张类";
            String type1 = "塑料类";
            String type2 = "玻璃类";
            String type3 = "金属类";
            String type4 = "纺织品";
            String type5 = "金属类";
            String type6 = "农药包装";

            String[] names = {
                    "纸壳、硬纸板",
                    "废旧资料、旧报纸 ",
                    "废旧塑料制品",
                    "饮料瓶 ",
                    "玻璃瓶",
                    "废旧玻璃渣",
                    "废铁、废铁片 ",
                    "废铝",
                    "废黄铜 ",
                    "废红铜",
                    "废旧衣物 ",
                    "废电池",
                    "废灯管 ",
                    "废旧灯泡",
                    "废旧农药空瓶 ",
                    "废旧农药袋"};

            String[] unit = {
                    "斤",
                    "斤",
                    "斤",
                    "个",
                    "斤",
                    "斤",
                    "斤",
                    "斤",
                    "斤",
                    "斤",
                    "斤",
                    "节",
                    "个",
                    "个",
                    "个",
                    "个"};

            double[] score = {
                    25,
                    60,
                    5,
                    7,
                    1,
                    40,
                    100,
                    70,
                    1750,
                    350,
                    10,
                    1,
                    1,
                    1,
                    2,
                    1};

            Garbage[] garbage = new Garbage[16];
            for (int i = 0; i < 16; i++) {
                garbage[i] = new Garbage();
                garbage[i].setRecyclePrice(1.0);
                garbage[i].setSuggestPrice(1.0);
                garbage[i].setShowAble(true);
                garbage[i].setScore(score[i]);
                garbage[i].setUnit(unit[i]);
                garbage[i].setName(names[i]);

                if (i < 2) {
                    garbage[i].setType(type);
                } else if (i < 4) {
                    garbage[i].setType(type1);
                } else if (i < 6) {
                    garbage[i].setType(type2);
                } else if (i < 10) {
                    garbage[i].setType(type3);
                } else if (i < 11) {
                    garbage[i].setType(type4);
                } else if (i < 14) {
                    garbage[i].setType(type5);
                } else {
                    garbage[i].setType(type6);
                }


                if (i < 11) {
                    garbage[i].setCategory(RecycleCategory);
                } else {
                    garbage[i].setCategory(UnRecycleCategory);
                }
                String picture = "http://localhost:8080/static/" + (i + 1) + ".jpg";
                garbage[i].setPicture(picture);
                garbageRepository.save(garbage[i]);

            }
        }
        if (userService.getGarbage("煤渣、灰土") == null) {
            String category = "自利用垃圾";
            String type = "自利用";
            String name = "煤渣、灰土";
            String unit = "斤";
            double score = 0.1;
            Garbage garbage = new Garbage();
            garbage.setShowAble(true);
            String picture = "http://localhost:8080/static/" + 17 + ".jpg";
            garbage.setCategory(category);
            garbage.setRecyclePrice(1.0);
            garbage.setSuggestPrice(1.0);
            garbage.setType(type);
            garbage.setUnit(unit);
            garbage.setName(name);
            garbage.setScore(score);
            garbage.setPicture(picture);
            garbageRepository.save(garbage);
        }

        if (userService.getGarbage("其他垃圾") == null) {
            String category = "不可回收垃圾";
            String type = "其他垃圾";
            String name = "其他垃圾";
            String unit = "斤";
            double score = 0;
            Garbage garbage = new Garbage();
            garbage.setShowAble(true);
            String picture = "http://localhost:8080/static/" + 17 + ".jpg";
            garbage.setCategory(category);
            garbage.setRecyclePrice(1.0);
            garbage.setSuggestPrice(1.0);
            garbage.setType(type);
            garbage.setName(name);
            garbage.setUnit(unit);
            garbage.setScore(score);
            garbage.setPicture(picture);
            garbageRepository.save(garbage);
        }

        if (userService.getGarbage("厨余垃圾") == null) {
            String category = "不可回收垃圾";
            String type = "厨余垃圾";
            String name = "厨余垃圾";
            String unit = "升";
            double score = 0;
            Garbage garbage = new Garbage();
            garbage.setShowAble(true);
            String picture = "http://localhost:8080/static/" + 19 + ".jpg";
            garbage.setRecyclePrice(1.0);
            garbage.setSuggestPrice(1.0);
            garbage.setCategory(category);
            garbage.setType(type);
            garbage.setName(name);
            garbage.setUnit(unit);
            garbage.setScore(score);
            garbage.setPicture(picture);
            garbageRepository.save(garbage);
        }
        // 危废垃圾就要计数就行，那就不放到订单里面了
        if (recycleDriverRepository.getById(0) == null) {
            RecycleDriver recycleDriver = new RecycleDriver();
            recycleDriver.setAddress("村口二甲");
            recycleDriver.setName("王二田");
            recycleDriver.setPhoneNumber("13050406564");
            recycleDriverRepository.save(recycleDriver);
        }
    }
}
