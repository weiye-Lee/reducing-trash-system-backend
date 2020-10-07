package com.work.recycle.component;


import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class InitComponent implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        // 项目初始化

    }
}
