package com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client;

import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.config.RoomForYouApiConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RoomForYouWebSecurityApiClient {
    private final RoomForYouApiConfig roomForYouApiConfig;
    private final RestTemplate restTemplate;

    public void register() {
        restTemplate.getForObject(buildBasicUrl().toString(), null);
    }

    private StringBuilder buildBasicUrl() {
        return  new StringBuilder().append(roomForYouApiConfig.getUrl())
                .append(roomForYouApiConfig.getVersion())
                .append("signup");
    }
}
