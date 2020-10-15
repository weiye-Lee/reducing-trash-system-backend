package com.work.recycle.component;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import com.work.recycle.entity.User;
import com.work.recycle.repository.BaseRepository;
import com.work.recycle.repository.UserRepository;
import com.work.recycle.repository.impl.BaseRepositoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
@SpringBootTest
@Slf4j
public class Testsome {

    @Autowired
    private UserRepository userRepository;

    private JpaEntityInformation<User,Integer> jpaEntityInformation;
    private EntityManager manager;

    private User user;

    @Test
    void testSome() {
      log.warn(User.class.toString());
    }

    @Test
    void test_format() {
        String d = "1.22";
        double ans = Double.parseDouble(d);
        log.warn("{}",ans);
    }

}
