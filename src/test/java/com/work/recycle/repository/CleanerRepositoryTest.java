package com.work.recycle.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class CleanerRepositoryTest {
    @Autowired
    private CleanerRepository cleanerRepository;
    @Test
    void test_addCleaner() {
    }

}