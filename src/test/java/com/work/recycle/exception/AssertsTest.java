package com.work.recycle.exception;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
class AssertsTest {

    @Test
    void fail() {
        Asserts.fail("能运行吗");
    }

    @Test
    void testFail() {
    }
}