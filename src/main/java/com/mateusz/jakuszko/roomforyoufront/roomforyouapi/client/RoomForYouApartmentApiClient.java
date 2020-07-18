package com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client;


import com.mateusz.jakuszko.roomforyoufront.dto.ApartmentDto;
import com.mateusz.jakuszko.roomforyoufront.mapper.ApartmentMapper;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.config.RoomForYouApiConfig;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response.ApartmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RoomForYouApartmentApiClient {
    private final ApartmentMapper apartmentMapper;
    private final RoomForYouApiConfig roomForYouApiConfig;
    private final RestTemplate restTemplate;

    public List<ApartmentDto> getApartmentsResponse() {
        return apartmentMapper.mapToApartmentDtos(getApartmentResponseList(buildBasicUrl().toString()));
    }

    public ApartmentDto postForApartment(ApartmentDto apartmentDto) {
        return apartmentMapper.mapToApartmentDto(Objects.requireNonNull(restTemplate
                .postForObject(buildBasicUrl().toString(), apartmentDto, ApartmentResponse.class)));
    }

    public void deleteApartment(Long id) {
        restTemplate.delete(buildBasicUrl().append(id).toString());
    }

    public List<ApartmentResponse> getApartmentResponseList(String url) {
        ApartmentResponse[] apartments = restTemplate.getForObject(url, ApartmentResponse[].class);
        if (apartments != null) {
            return Arrays.asList(apartments);
        }
        return new ArrayList<>();
    }

    private StringBuilder buildBasicUrl() {
        return  new StringBuilder().append(roomForYouApiConfig.getUrl())
                .append(roomForYouApiConfig.getVersion())
                .append(roomForYouApiConfig.getApartment());
    }
}
