package com.work.recycle.service;

import com.work.recycle.common.ResultCode;
import com.work.recycle.entity.*;
import com.work.recycle.exception.ApiException;
import com.work.recycle.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
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

    private User validateUser(User user) {
        if (user.getName() == null || user.getPhoneNumber() == null) {
            throw new ApiException("数据不全");
        }
        user.setId(null);
        return user;
    }

    public User addCleaner(User user) {
        user = validateUser(user);
        Cleaner cleaner = new Cleaner(user);
        cleanerRepository.save(cleaner);
        return user;
    }

    public User addDriver(User user) {
        user = validateUser(user);
        Driver driver = new Driver();
        driver.setUser(user);
        driverRepository.save(driver);
        return user;
    }

    public User addRecycleFirm(User user) {
        user = validateUser(user);
        RecycleFirm recycleFirm = new RecycleFirm();
        recycleFirm.setUser(user);
        recycleFirmRepository.save(recycleFirm);
        return user;
    }

    public User addTransferStation(User user) {
        user = validateUser(user);
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
                .ifPresentOrElse(farmer -> farmer.setCleaner(newCleaner)
                        , () -> {
                            throw new ApiException("农户信息错误");
                        }));

        // 将fc订单转移到新的保洁员身上
        fcOrderRepository.getCleanerFCOrdersById(oldId, false)
                .forEach(a -> a.setCleaner(newCleaner));

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
                .ifPresentOrElse(cleaner -> cleaner.setDriver(newDriver)
                        , () -> {
                            throw new ApiException("农户信息错误");
                        }));

        cdOrderRepository.getCDOrdersByDriverAndBaseOrder(oldId,false)
                .forEach(a -> a.setDriver(newDriver));

        return cleanerIds.size();
    }
}
