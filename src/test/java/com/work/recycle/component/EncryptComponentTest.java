package com.work.recycle.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.work.recycle.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


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

        MyToken myToken = new MyToken(User.Role.FARMER,3);
        MyToken myToken1 = new MyToken(User.Role.CLEANER, 2);
        MyToken myToken2 = new MyToken(User.Role.DRIVER, 1);
        log.warn(encryptComponent.encryptToken(myToken));
        log.warn(encryptComponent.encryptToken(myToken1));
        log.warn(encryptComponent.encryptToken(myToken2));

        /*
            farmer
            139c2fbebadbd5a7d23baa1933854de84a3516e2c3b6cd8923ae7d40d1da3208807c992e36c3c75f447151d4270b7612

            cleaner
            c7f9fb9a8461a21c669b6cd05d4fab40852a1c7c8cce406802034a19a22140b0e3d69a5f46c7f3b2bef6219155aa7d57
            driver
            7ebb247b73fb86cf1a8228091ea0fab4334e6ee2795c2a3a5d5fe85c73fd226ddc4e5dbd7b29078d22a32e5852df26de

         */
    }

    @Test
    void test_encrypt() {
        String auth = "96a45160cb1db954294ecc22baba66cc8c2e1bac3023c735ae00877c5b3de0855ca53402b3284a1690309f8c369f1c5e596165042e390f0964ddb2cb3435fb21";
        MyToken myToken = encryptComponent.decryptToken(auth);
        log.warn("{}",myToken.getUid());

    }

    @Test
    void mkr_cleanerToken() {
    }
}