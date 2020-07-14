package com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client;


import com.mateusz.jakuszko.roomforyoufront.dto.ApartmentDto;
import com.mateusz.jakuszko.roomforyoufront.mapper.ApartmentMapper;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.config.RoomForYouApiConfig;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response.ApartmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomForYouApartmentApiClient {
    private final ApartmentMapper apartmentMapper;
    private final RoomForYouApiConfig roomForYouApiConfig;
    private final RestTemplate restTemplate;

    public List<ApartmentDto> getApartmentsResponse() {
        StringBuilder url = new StringBuilder();
        url.append(roomForYouApiConfig.getUrl()).append(roomForYouApiConfig.getVersion()).append(roomForYouApiConfig.getApartment());
        return apartmentMapper.mapToApartmentDtos(getApartmentResponseList(url.toString()));
    }

    public ApartmentDto postForApartment(ApartmentDto apartmentDto) {
        StringBuilder url = new StringBuilder();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //String jsonApartment = apartmentMapper.mapToJsonObject(apartmentDto);
//        apartmentDto = ApartmentDto.builder()
//                .city("Poznań").customerId(1L).build();
        HttpEntity<ApartmentDto> request = new HttpEntity<ApartmentDto>(apartmentDto, headers);
        url.append(roomForYouApiConfig.getUrl()).append(roomForYouApiConfig.getVersion()).append(roomForYouApiConfig.getApartment());
        return apartmentMapper.mapToApartmentDto(restTemplate.postForObject(url.toString(), apartmentDto, ApartmentResponse.class));
    }

    public List<ApartmentResponse> getApartmentResponseList(String url) {
        ApartmentResponse[] apartments = restTemplate.getForObject(url, ApartmentResponse[].class);
        if (apartments != null) {
            return Arrays.asList(apartments);
        }
        return new ArrayList<>();
    }



}
