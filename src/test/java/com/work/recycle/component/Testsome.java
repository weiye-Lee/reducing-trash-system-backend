package com.work.recycle.component;

import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.Garbage;
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
    @Autowired
    private ObjectMapper mapper;

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

    @Test
    void noName() {
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
        log.warn("{}",score.length);
    }

    @Test
    void test_init() {
        String RecycleCategory = "可回收垃圾";
        String UnRecycleCatagory = "不可回收垃圾";
        String type = "纸张类";
        String type1 = "塑料类";
        String type2 = "玻璃类";
        String type3 = "金属类";
        String type4 = "纺织品";
        String type5 = "金属类";
        String type6 = "农药包装";

        String[] names = {"纸壳、硬纸板",
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

        String[] unit = {"斤",
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

        double[] score = {25,
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

        log.warn("{}",score[0]);
        Garbage[] garbage = new Garbage[16];
        for (int i = 0; i < 16; i++) {
            garbage[i] = new Garbage();
            garbage[i].setScore(score[i]);
            garbage[i].setUnit(unit[i]);
            garbage[i].setName(names[i]);

            if (i < 2 ) {
                garbage[i].setType(type);
            }else if (i < 4) {
                garbage[i].setType(type1);
            }else  if (i < 6) {
                garbage[i].setType(type2);
            }else if (i < 10) {
                garbage[i].setType(type3);
            }else if (i < 11) {
                garbage[i].setType(type4);
            }else if (i < 14) {
                garbage[i].setType(type5);
            }else {
                garbage[i].setType(type6);
            }


            if (i < 11 ) {
                garbage[i].setCategory(RecycleCategory);
            }else {
                garbage[i].setCategory(UnRecycleCatagory);
            }

            log.warn(garbage[i].toString());
        }
    }



}
