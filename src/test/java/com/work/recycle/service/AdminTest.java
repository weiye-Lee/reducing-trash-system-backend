package com.work.recycle.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.*;
import com.work.recycle.repository.DriverRepository;
import com.work.recycle.repository.RecycleFirmRepository;
import com.work.recycle.repository.TransferStationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
class AdminTest {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private RecycleFirmRepository recycleFirmRepository;
    @Autowired
    private TransferStationRepository transferStationRepository;
    @Test
    void addCleaner() {
    }

    @Test
    void addDriver() throws JsonProcessingException {
        Driver driver = new Driver();
        User user = new User();
        UserRole userRole = new UserRole();
        userRole.setRole(UserRole.Role.DREIVER);
        userRole.setRoleName("司机");
        List<UserRole> list = new ArrayList<UserRole>();
        list.add(userRole);
        user.setRoleList(list);

        user.setPhoneNumber("13050462540");
        user.setName("司机");
        driver.setUser(user);
        log.warn(mapper.writeValueAsString(driver));
        driverRepository.save(driver);
    }

    @Test
    void addRecycleFirm() {
        User user = new User();
        user.setName("回收企业呦");
        user.setPhoneNumber("110");
        RecycleFirm recycleFirm = new RecycleFirm();
        recycleFirm.setUser(user);

        recycleFirmRepository.save(recycleFirm);
    }

    @Test
    void addTransferStation() {
        User user = new User();
        user.setName("中转站呦");
        user.setPhoneNumber("110");
        TransferStation transferStation = new TransferStation();
        transferStation.setUser(user);

        transferStationRepository.save(transferStation);
    }
}