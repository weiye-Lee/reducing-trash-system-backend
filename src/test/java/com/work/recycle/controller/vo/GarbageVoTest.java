package com.work.recycle.controller.vo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GarbageVoTest {

    @Test
    void iscategory() {
        String name = "煤渣、灰土";
        System.out.println(GarbageVo.iscategory(name));

    }
}