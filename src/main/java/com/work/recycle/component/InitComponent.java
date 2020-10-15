package com.work.recycle.component;


import com.work.recycle.entity.*;
import com.work.recycle.repository.*;
import com.work.recycle.service.FarmerService;
import com.work.recycle.service.UserService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InitComponent implements InitializingBean {
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
    @Override
    public void afterPropertiesSet() throws Exception {

        User u = userService.getUserByPhone("13050496540");
        if (u == null) {
            User user3 = new User();
            user3.setName("司机姓名");
            user3.setSex(User.Sex.MALE);
            user3.setRole(User.Role.DRIVER);
            user3.setPhoneNumber("13050496540");
            Driver driver = new Driver();
            driver.setUser(user3);
            driverRepository.save(driver);
        }


        u = userService.getUserByPhone("13050496541");
        if (u == null) {
            User user2 = new User();
            user2.setName("保洁员姓名");
            user2.setSex(User.Sex.MALE);
            user2.setRole(User.Role.CLEANER);
            user2.setPhoneNumber("13050496541");
            Cleaner cleaner = new Cleaner();
            cleaner.setUser(user2);
            cleaner.setDriver(driverRepository.getDriverByPhoneNumber("13050496540"));
            cleanerRepository.save(cleaner);
        }

        u = userService.getUserByPhone("13050496542");
        if (u == null) {
            User user = new User();
            user.setName("农户姓名");
            user.setSex(User.Sex.MALE);
            user.setRole(User.Role.FARMER);
            user.setPhoneNumber("13050496542");
            Farmer farmer = new Farmer();
            farmer.setUser(user);
            farmer.setCleaner(cleanerRepository.getCleanerByPhoneNumber("13050496541"));
            farmerRepository.save(farmer);
        }


        Garbage g = userService.getGarbage("二手飞机");
        if (g == null) {
            Garbage garbage = new Garbage();
            garbage.setName("二手飞机");
            garbage.setCategory("可回收垃圾");
            garbage.setUnit("个");
            garbage.setScore(1000);
            garbageRepository.save(garbage);
        }

        g = userService.getGarbage("厨余垃圾");
        if (g == null) {
            Garbage garbage2 = new Garbage();
            garbage2.setName("厨余垃圾");
            garbage2.setCategory("不可回收垃圾");
            garbage2.setUnit("斤");
            garbage2.setScore(0);
            garbageRepository.save(garbage2);
        }

    }
}
