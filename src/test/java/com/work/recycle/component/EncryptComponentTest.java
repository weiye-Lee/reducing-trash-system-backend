package com.work.recycle.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    void encryptToken() throws JsonProcessingException {
        MyToken myToken = new MyToken("123456");
        log.warn(mapper.writeValueAsString(myToken));
        // String json = encryptComponent.encryptToken(myToken);
        // log.warn(json);
        // myToken = encryptComponent.decryptToken(json);
        // log.warn("{}",mapper.writeValueAsString(myToken));
        // log.warn("{}",myToken);

    }

    @Test
    void decryptToken() throws JsonProcessingException {
        String token = "5f99188020c852c4da2ee253d92cdce90b09af1c1c5f3aff62bfbd787b7ed2be0681ee30869f01850415d14fffdbadf1";

        MyToken myToken = encryptComponent.decryptToken(token);

        log.warn(mapper.writeValueAsString(myToken));
    }
}