package com.work.recycle.component;

import com.fasterxml.jackson.databind.ser.std.ObjectArraySerializer;
import com.work.recycle.entity.Cleaner;
import com.work.recycle.entity.User;
import com.work.recycle.repository.BaseRepository;
import com.work.recycle.service.CleanerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Method;

@Slf4j
@SpringBootTest
public class TestInterface {
    @Autowired
    private BaseRepository baseRepository;
    @Test
    <T> void addUser(User user,T t) throws Exception {
        Class clz = t.getClass();
        Method addUser_method = clz.getMethod("setUser",User.class);
        addUser_method.invoke(t,user);
        log.warn(t.toString());
    }
    @Test
    void test_addUser() throws Exception {
        User user = new User();
        user.setName("测试使用泛型插入用户");
        TestInterface testInterface = new TestInterface();
        testInterface.addUser(user,new Cleaner());
    }
}
