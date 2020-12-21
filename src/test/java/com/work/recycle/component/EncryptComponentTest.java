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
        String token = "e2cf25451c687a1c48b0e6a74bd8303af273e18f8b60dbbddf610ff715ba0362ce36cee522e3adf17fe2dde1c927b2763eb6f633dcf42fbc44eb20cf2c4a8e05";
        MyToken myToken = encryptComponent.decryptToken(token);

        log.warn(mapper.writeValueAsString(myToken));
    }

    // 生成几个用户token
    @Test
    void testEncrypt() {

        MyToken myToken = new MyToken(User.Role.FARMER,3);
        MyToken myToken1 = new MyToken(User.Role.CLEANER, 2);
        MyToken myToken2 = new MyToken(User.Role.DRIVER, 1);
        MyToken myToken3 = new MyToken(User.Role.TRANSFERSTATION,4);
        MyToken myToken4 = new MyToken(User.Role.RECYCLEFIRM,5);
        MyToken myToken5 = new MyToken(User.Role.ADMIN,61);
        log.warn(encryptComponent.encryptToken(myToken));
        log.warn(encryptComponent.encryptToken(myToken1));
        log.warn(encryptComponent.encryptToken(myToken2));
        log.warn(encryptComponent.encryptToken(myToken3));
        log.warn(encryptComponent.encryptToken(myToken4));
        log.warn("admin="+encryptComponent.encryptToken(myToken5));

        /*
            farmer
            139c2fbebadbd5a7d23baa1933854de84a3516e2c3b6cd8923ae7d40d1da3208807c992e36c3c75f447151d4270b7612

            cleaner
            c7f9fb9a8461a21c669b6cd05d4fab40852a1c7c8cce406802034a19a22140b0e3d69a5f46c7f3b2bef6219155aa7d57

            driver
            7ebb247b73fb86cf1a8228091ea0fab4334e6ee2795c2a3a5d5fe85c73fd226ddc4e5dbd7b29078d22a32e5852df26de

            recycleFirm
            621ecd13f84b1bfee8fc7dcd2ce2c538068f9b9272039187efd6946302775132f8a88001ab8912cc7123e4e07d9a9bec

            transferStaion

            154040a9ba5aa6c919742ae41b1e39b4b9784931d6ae29495f267f432eae0bd08e1e52b6d5b0fb6562856c6d26e890d2788ca59a0a5f92da500ce6502d30a083

            admin

            d173d91045a96d6a0350969c17a8ac077082b7f98448491c2cd477ecdb61b9985be10d33db85874e3fe1606c33de3588
         */
    }

    @Test
    void test_encrypt() {
        String auth = "dbf0eb6d717f08e22cf1fdc8e4514d3bfa023e04c0b719020f16d3cdeb52ae642c28726c1eb5271e864b87c357f0dc64598c08cb68032b5c0b5afbc3d214ae81";
        MyToken myToken = encryptComponent.decryptToken(auth);
        try {
            log.warn(mapper.writeValueAsString(myToken));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @Test
    void mkr_cleanerToken() {
    }
}