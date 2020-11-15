package com.work.recycle.repository;

import com.work.recycle.entity.Cleaner;
import com.work.recycle.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
@Slf4j
class CleanerRepositoryTest {
    @Autowired
    private CleanerRepository cleanerRepository;
    @Autowired
    private PasswordEncoder encoder;
    @Test
    void test_addCleaner() {
    }

    @Test
    void findTop10() {
        cleanerRepository.findTop10ByOrderByScoreDesc()
                .forEach(a -> System.out.println(a.getScore()));
    }

    @Test
    void findTop10ByUser_VillageOrderByScoreDesc() {
        List<Cleaner> cleanerList = cleanerRepository.findTop10ByUser_VillageOrderByScoreDesc("鹤岗村");
        log.warn("some wrong");
        cleanerList.forEach(a -> log.warn("{}",a.getScore()));
    }

    @Test
    void test_rankList() {
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("保洁员姓名");
            String phone = "1305049656" + i;
            user.setPhoneNumber(phone);
            user.setPassword(encoder.encode("123456"));
            user.setRole(User.Role.CLEANER);
            user.setVillage("鹤岗村");
            Cleaner cleaner = new Cleaner();
            cleaner.setUser(user);
            cleaner.setScore(i);
            cleanerRepository.save(cleaner);
        }
    }

}