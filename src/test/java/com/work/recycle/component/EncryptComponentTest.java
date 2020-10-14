package com.work.recycle.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.UserRole;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class EncryptComponentTest {
    @Autowired
    private EncryptComponent encryptComponent;
    @Autowired
    private ObjectMapper mapper;

    @Test
    void getTextEncryptor() {
    }

    @Test
    void decryptToken() throws JsonProcessingException {
        String token = "a7b45ad346bd01b1ad9798751f3ac59ff5256936e24d8d6fb42c78b8f9ad08a51f8aa76d62977d71bd3cb520800c7e700e9020e2878342c20c5e6139c6b89af7";
        MyToken myToken = encryptComponent.decryptToken(token);

        log.warn(mapper.writeValueAsString(myToken));
    }

    // 生成几个用户token
    @Test
    void testEncrypt() {
        MyToken myToken = new MyToken("0", UserRole.Role.FARMER, 1);
        MyToken myToken1 = new MyToken("0", UserRole.Role.CLEANER, 5);
        MyToken myToken2 = new MyToken("0", UserRole.Role.DREIVER, 7);
        log.warn(encryptComponent.encryptToken(myToken));
        log.warn(encryptComponent.encryptToken(myToken1));
        log.warn(encryptComponent.encryptToken(myToken2));

        /*
            farmer
            96a45160cb1db954294ecc22baba66cc8c2e1bac3023c735ae00877c5b3de0855ca53402b3284a1690309f8c369f1c5e596165042e390f0964ddb2cb3435fb21


            cleaner
            d6358ed60c3f72d93f0594fe7f5b2474aa17067c0e5927c6138cd5554e0f90fbcf8c8212b8eec2d0bd4718ec95ec6cba0240ded7e377229a7f3c8ccd4ae7b993


            driver
            54f92eec9d35f7c1c82957373ef7d3928f5c0dec1b2ffba46a9c5067282890781413c09263428887eaadfdf215fb0a884ed56ff4ebeece73b0349ea4c1341f2f


         */
    }
}