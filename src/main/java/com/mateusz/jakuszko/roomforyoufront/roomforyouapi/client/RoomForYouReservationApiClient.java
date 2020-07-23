package com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client;

import com.mateusz.jakuszko.roomforyoufront.dto.ReservationDto;
import com.mateusz.jakuszko.roomforyoufront.mapper.ReservationMapper;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.config.RoomForYouApiConfig;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class RoomForYouReservationApiClient {
    private final ReservationMapper reservationMapper;
    private final RoomForYouApiConfig roomForYouApiConfig;
    private final RestTemplate restTemplate;

    public List<ReservationDto> getReservationResponse() {
        return reservationMapper.mapToReservationDtos(getReservationResponseList(buildBasicUrl().toString()));
    }

    public ReservationDto postForReservation(ReservationDto reservationDto) {
        return reservationMapper.mapToReservationDto(Objects.requireNonNull(restTemplate.postForObject(buildBasicUrl().toString(),
                reservationDto, ReservationResponse.class)));
    }

    public void deleteReservation(Long id) {
        restTemplate.delete(buildBasicUrl().append(id).toString());
    }

    private List<ReservationResponse> getReservationResponseList(String url) {
        ReservationResponse[] customers = restTemplate.getForObject(url, ReservationResponse[].class);
        if (customers != null) {
            return Arrays.asList(customers);
        }
        return new ArrayList<>();
    }

    private StringBuilder buildBasicUrl() {
        return  new StringBuilder().append(roomForYouApiConfig.getUrl())
                .append(roomForYouApiConfig.getVersion())
                .append(roomForYouApiConfig.getReservation());
    }
}
