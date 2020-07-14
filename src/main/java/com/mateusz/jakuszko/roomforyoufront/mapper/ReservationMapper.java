package com.mateusz.jakuszko.roomforyoufront.mapper;

import com.google.gson.Gson;
import com.mateusz.jakuszko.roomforyoufront.dto.ApartmentDto;
import com.mateusz.jakuszko.roomforyoufront.dto.ReservationDto;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response.ReservationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ReservationMapper {
    public List<ReservationDto> mapToReservationDtos(List<ReservationResponse> reservationResponses) {
        log.info("Map ReservationResponses to ReservationDtos");
        return reservationResponses.stream()
                .map(e -> ReservationDto.builder()
                .id(e.getId())
                .startDate(e.getStartDate())
                .endDate(e.getEndDate())
                .apartmentId(e.getApartmentId())
                .userId(e.getUserId())
                .build())
                .collect(Collectors.toList());
    }

    public ReservationDto mapToReservationDto(ReservationResponse reservationResponse) {
        log.info("Map ReservationResponse to ReservationDto");
        return ReservationDto.builder()
                .id(reservationResponse.getId())
                .startDate(reservationResponse.getStartDate())
                .endDate(reservationResponse.getEndDate())
                .apartmentId(reservationResponse.getApartmentId())
                .userId(reservationResponse.getUserId())
                .build();
    }

    public String mapToJsonObject(ReservationDto reservationDto) {
        log.info("Map ApartmentDto to JsonObject");
        Gson gson = new Gson();
        return gson.toJson(reservationDto);
    }
}
