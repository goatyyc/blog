package com.learn.site;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.learn.site.dao")
@EnableCaching
public class SiteApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(SiteApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
//        SpringApplication.run(SiteApplication.class, args);

    }

}
