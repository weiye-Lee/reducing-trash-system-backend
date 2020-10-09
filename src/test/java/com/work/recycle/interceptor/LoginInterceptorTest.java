package com.work.recycle.interceptor;

import com.work.recycle.component.EncryptComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpServerErrorException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LoginInterceptorTest {
    @Autowired
    private EncryptComponent encryptComponent;

    @Test
    void test_encrypt() {
        throw new HttpServerErrorException(
                HttpStatus.ACCEPTED
        );
    }
}