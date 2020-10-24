package com.work.recycle.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class Testconstructor {
    @Autowired
    private ObjectMapper mapper;
    @Test
    public void test_t() throws JsonProcessingException {
        TestC t = new TestC("done");
        log.warn(t.toString());
        log.warn(mapper.writeValueAsString(t));
    }
}

class TestC {
    private final String name;


    TestC(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "TestC{" +
                "name='" + name + '\'' +
                '}';
    }

    // WriteValueAsString 的序列化依靠类中的getter方法，如果方法为空 测无法序列化
    //
    public String getName() {
        return "wieye";
    }
}
