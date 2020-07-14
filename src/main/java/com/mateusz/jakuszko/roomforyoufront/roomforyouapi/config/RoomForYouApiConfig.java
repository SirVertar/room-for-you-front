package com.mateusz.jakuszko.roomforyoufront.roomforyouapi.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "room.for.you.api")
@Getter
@Setter
public class RoomForYouApiConfig {
    String url;
    String version;
    String apartment;
    String reservation;
    String customer;
}
