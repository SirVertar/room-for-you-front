package com.mateusz.jakuszko.roomforyoufront;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@SpringBootApplication
//@EnableConfigurationProperties
//public class RoomForYouFrontApplication {
//    public static void main(String[] args) {
//        SpringApplication.run(RoomForYouFrontApplication.class, args);
//    }
//}

@SpringBootApplication
@EnableConfigurationProperties
public class RoomForYouFrontApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(RoomForYouFrontApplication.class, args);
    }
//
//    @Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(RoomForYouFrontApplication.class);
//    }
}
