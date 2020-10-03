package com.work.recycle.repository;

import com.work.recycle.entity.Cleaner;
import com.work.recycle.entity.User;
import com.work.recycle.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class CleanerRepositoryTest {
    @Autowired
    private CleanerRepository cleanerRepository;
    @Test
    void test_addCleaner() {
        User user = new User();
        user.setPhoneNumber("789");
        user.setName("保洁员呦");
        UserRole role = new UserRole();
        role.setRole(UserRole.Role.CLEANER);
        List<UserRole> list = new ArrayList<>();
        list.add(role);
        user.setRoleList(list);
        Cleaner cleaner = new Cleaner();
        cleaner.setUser(user);
        cleanerRepository.save(cleaner);
    }

}