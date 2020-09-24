package com.work.recycle.component;

import com.work.recycle.entity.Farmer;
import com.work.recycle.entity.Garbage;
import com.work.recycle.entity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

@Component
public class InitComponent implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        // 项目初始化

        // 插入一条农户数据
        Farmer farmer = new Farmer();
        User user = new User();
        user.setName("保洁员");
        farmer.setUser(user);
    }
}
