package com.work.recycle.service;

import com.google.common.util.concurrent.AtomicDouble;
import com.work.recycle.common.ResultCode;
import com.work.recycle.controller.vo.GarbageVo;
import com.work.recycle.entity.*;
import com.work.recycle.exception.ApiException;
import com.work.recycle.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class AdminService {
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private RecycleDriverRepository recycleDriverRepository;
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private FarmerRepository farmerRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private RecycleFirmRepository recycleFirmRepository;
    @Autowired
    private TransferStationRepository transferStationRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FCOrderRepository fcOrderRepository;
    @Autowired
    private CDOrderRepository cdOrderRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private GarbageRepository garbageRepository;
    @Autowired
    private RecyclePriceRepository recyclePriceRepository;
    @Autowired
    private SuggestPriceRepository suggestPriceRepository;

    private User validateUser(User user) {
        if (user.getName() == null || user.getPhoneNumber() == null) {
            throw new ApiException("数据不全");
        }
        user.setId(null);
        user.setPassword(encoder.encode("123456"));
        return user;
    }

    public User addCleaner(User user) {
        user = validateUser(user);
        user.setRole(User.Role.CLEANER);
        Cleaner cleaner = new Cleaner(user);
        cleanerRepository.save(cleaner);
        return user;
    }

    public User addDriver(User user) {
        user = validateUser(user);
        user.setRole(User.Role.DRIVER);
        Driver driver = new Driver();
        driver.setUser(user);
        driverRepository.save(driver);
        return user;
    }
    // todo test it
    public RecycleDriver addRecycleDriver(RecycleDriver recycleDriver) {
        recycleDriverRepository.save(recycleDriver);
        return recycleDriver;
    }

    public User addRecycleFirm(User user) {
        user = validateUser(user);
        user.setRole(User.Role.RECYCLEFIRM);
        RecycleFirm recycleFirm = new RecycleFirm();
        recycleFirm.setUser(user);
        recycleFirmRepository.save(recycleFirm);
        return user;
    }

    public User addTransferStation(User user) {
        user = validateUser(user);
        user.setRole(User.Role.TRANSFERSTATION);
        TransferStation transferStation = new TransferStation();
        transferStation.setUser(user);
        transferStationRepository.save(transferStation);
        return user;
    }

    public User getUserInfo(int id) {
        return userRepository.findById(id)
                .orElseThrow(() -> {
                    throw new ApiException("id错误");
                });
    }

    public List<User> getCleaner() {
        return userRepository.findByRole(User.Role.CLEANER);
    }

    public List<User> getDriver() {
        return userRepository.findByRole(User.Role.DRIVER);
    }

    public List<User> getRecyclefirm() {
        return userRepository.findByRole(User.Role.RECYCLEFIRM);
    }

    public List<User> getTransferStation() {
        return userRepository.findByRole(User.Role.TRANSFERSTATION);
    }

    public List<User> getFarmersByCleanerId(int id) {
        return farmerRepository.findByCleaner_Id(id)
                .stream()
                .map(Farmer::getUser)
                .collect(Collectors.toList());
    }

    public List<User> getCleanersByDriverId(int id) {
        return cleanerRepository.findByDriver_Id(id)
                .stream()
                .map(Cleaner::getUser)
                .collect(Collectors.toList());
    }

    public int transferCleaner(int oldId, int newId, List<Integer> farmerIds) {
        Cleaner oldCleaner = cleanerRepository.findById(oldId)
                .orElseThrow(() -> {
                    throw new ApiException("id不存在");
                });
        Cleaner newCleaner = cleanerRepository.findById(newId)
                .orElseThrow(() -> {
                    throw new ApiException("id不存在");
                });
        // 为农户定义新的保洁员
        farmerIds.forEach(id -> farmerRepository.findById(id)
                .ifPresentOrElse(farmer -> {
                            farmer.setCleaner(newCleaner);
                            farmerRepository.save(farmer);
                        }
                        , () -> {
                            throw new ApiException("农户信息错误");
                        }));

        // 将fc订单转移到新的保洁员身上
        fcOrderRepository.getCleanerFCOrdersById(oldId, false)
                .forEach(a -> {
                            a.setCleaner(newCleaner);
                            fcOrderRepository.save(a);
                        }
                );

        return farmerIds.size();
    }

    public int transferDriver(int oldId, int newId, List<Integer> cleanerIds) {
        Driver oldDriver = driverRepository.findById(oldId)
                .orElseThrow(() -> {
                    throw new ApiException("id不存在");
                });
        Driver newDriver = driverRepository.findById(newId)
                .orElseThrow(() -> {
                    throw new ApiException("id不存在");
                });

        cleanerIds.forEach(id -> cleanerRepository.findById(id)
                .ifPresentOrElse(cleaner -> {
                            cleaner.setDriver(newDriver);
                            cleanerRepository.save(cleaner);
                        }
                        , () -> {
                            throw new ApiException("农户信息错误");
                        }));


        cdOrderRepository.getCDOrdersByDriverAndBaseOrder(oldId, false)
                .forEach(a -> {
                    a.setDriver(newDriver);
                    cdOrderRepository.save(a);
                });

        return cleanerIds.size();
    }

    public Boolean setUsable(int id, Boolean bool) {
        User user = userRepository.getUserById(id);
        user.setUsable(bool);
        userRepository.save(user);
        return bool;
    }


    // todo test it
    public Garbage recordGarbage(Garbage garbage) {
        if (garbage == null) {
            throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
        }
        Optional.ofNullable(garbage)
                .map(Garbage::getId)
                .ifPresentOrElse(id -> {
                    Garbage real = garbageRepository.getGarbageById(id);
                    if (real != null) {
                        if (garbage.getRecyclePrice() == null) {
                            garbage.setRecyclePrice(real.getRecyclePrice());
                        }
                        if (garbage.getSuggestPrice() == null) {
                            garbage.setSuggestPrice(real.getSuggestPrice());
                        }
                        if (real.getRecyclePrice() != null) {

                            if (!real.getRecyclePrice().equals(garbage.getRecyclePrice())) {
                                RecyclePrice recyclePrice = new RecyclePrice();
                                recyclePrice.setGarbage(real);
                                recyclePrice.setUnit(real.getUnit());
                                recyclePrice.setPrice(real.getRecyclePrice());
                                recyclePriceRepository.save(recyclePrice);
                            }
                        }

                        if (real.getSuggestPrice() != null) {
                            if (!real.getSuggestPrice().equals(garbage.getSuggestPrice())) {
                                SuggestPrice suggestPrice = new SuggestPrice();
                                suggestPrice.setGarbage(real);
                                suggestPrice.setUnit(real.getUnit());
                                suggestPrice.setPrice(real.getSuggestPrice());
                                suggestPriceRepository.save(suggestPrice);
                            }
                        }
                        garbageRepository.save(garbage);
                    }
                }, () -> garbageRepository.save(garbage));


        // 如果新旧价格不同，那么将旧价格记录下来

        return garbage;
    }

    public News addNews(News news) {
        newsRepository.save(news);
        return news;
    }


}
