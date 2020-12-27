package com.work.recycle.service;

import com.work.recycle.common.ResultCode;
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
    private RecyclePriceListRepository recyclePriceListRepository;
    @Autowired
    private RecyclePriceRecordRepository recyclePriceRecordRepository;
    @Autowired
    private SuggestPriceListRepository suggestPriceListRepository;
    @Autowired
    private SuggestPriceRecordRepository suggestPriceRecordRepository;
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

    /**
     * 设置回收企业垃圾回收价格
     *
     * @param recyclePriceLists 新垃圾价格表，新价格表要包括所有的垃圾
     */
    public void setRecyclePrice(List<RecyclePriceList> recyclePriceLists) {
        if (recyclePriceLists == null) {
            throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
        }

        Map<Integer, Double> priceMap = new HashMap<>();
        Map<Integer, String> unitMap = new HashMap<>();
        /*
        recyclePriceLists.forEach(a -> {
            Double price = Optional.ofNullable(a)
                    .map(RecyclePriceList::getGarbage)
                    .map(Garbage::getRecyclePrice)
                    .orElseThrow(() -> {
                        throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
                    });

            String unit = Optional.ofNullable(a)
                    .map(RecyclePriceList::getGarbage)
                    .map(Garbage::getUnit)
                    .orElseThrow(() -> {
                        throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
                    });

            int id = Optional.ofNullable(a)
                    .map(RecyclePriceList::getGarbage)
                    .map(Garbage::getId)
                    .orElseThrow(() -> {
                        throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
                    });

            priceMap.put(id, price);
            unitMap.put(id, unit);


         */
        // 构造迭代器，遍历裂变，生成价格，单位和垃圾id的对应关系，并检查是否存在输入为空
        Iterator<RecyclePriceList> listIterator = recyclePriceLists.iterator();
        while (listIterator.hasNext()) {
            Garbage garbage = listIterator.next().getGarbage();
            if (garbage == null || garbage.getId() == null
                    || garbage.getRecyclePrice() == null
                    || garbage.getUnit() == null) {
                throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
            } else {
                priceMap.put(garbage.getId(), garbage.getRecyclePrice());
                unitMap.put(garbage.getId(), garbage.getUnit());
            }
        }

        // 更新最新垃圾回收价格表
        List<Garbage> garbageList = garbageRepository.findAll();
        garbageList.forEach(garbage -> {
            int garbageId = garbage.getId();
            if (priceMap.get(garbageId) == null || unitMap.get(garbageId) == null) {
                throw new ApiException(ResultCode.RESOURCE_NOT_FOUND);
            }
            garbage.setRecyclePrice(priceMap.get(garbageId));
            garbage.setRecyclePriceUnit(unitMap.get(garbageId));
        });

        // 新增价格改动记录
        RecyclePriceRecord recyclePriceRecord = new RecyclePriceRecord();
        recyclePriceRecordRepository.save(recyclePriceRecord);
        recyclePriceLists.forEach(a -> {
            a.setRecyclePriceRecord(recyclePriceRecord);
            recyclePriceListRepository.save(a);
        });
    }
}
