package com.work.recycle.config;

import com.work.recycle.interceptor.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MyWebAppConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private FarmerInterceptor farmerInterceptor;
    @Autowired
    private CleanerInterceptor cleanerInterceptor;
    @Autowired
    private DriverInterceptor driverInterceptor;
    @Autowired
    private RecycleFirmInterceptor recycleFirmInterceptor;
    @Autowired
    private TransferStationInterceptor transferStationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/user/login");

        registry.addInterceptor(farmerInterceptor)
                .addPathPatterns("/api/farmer/**");

        registry.addInterceptor(cleanerInterceptor)
                .addPathPatterns("/api/cleaner/**");

        registry.addInterceptor(driverInterceptor)
                .addPathPatterns("/api/driver/**");

        registry.addInterceptor(recycleFirmInterceptor)
                .addPathPatterns("/api/recycleFirm/**");

        registry.addInterceptor(transferStationInterceptor)
                .addPathPatterns("/api/transferStation/**");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");

    }

}
