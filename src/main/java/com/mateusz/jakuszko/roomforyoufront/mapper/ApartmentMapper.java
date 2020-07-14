package com.mateusz.jakuszko.roomforyoufront.mapper;

import com.google.gson.Gson;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response.ApartmentResponse;
import com.mateusz.jakuszko.roomforyoufront.dto.ApartmentDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ApartmentMapper {
    public List<ApartmentDto> mapToApartmentDtos(List<ApartmentResponse> apartments) {
        log.info("Map ApartmentResponses to ApartmentDtos");
        return apartments.stream()
                .map(apartmentResponse -> ApartmentDto.builder()
                        .id(apartmentResponse.getId())
                        .latitude(apartmentResponse.getLatitude())
                        .longitude(apartmentResponse.getLongitude())
                        .city(apartmentResponse.getCity())
                        .street(apartmentResponse.getStreet())
                        .streetNumber(apartmentResponse.getStreetNumber())
                        .apartmentNumber(apartmentResponse.getApartmentNumber())
                        .customerId(apartmentResponse.getCustomerId())
                        .reservationsIds(apartmentResponse.getReservationsIds()).build())
                .collect(Collectors.toList());
    }

    public ApartmentDto mapToApartmentDto(ApartmentResponse apartment) {
        log.info("Map ApartmentResponse to ApartmentDto");
        return ApartmentDto.builder()
                .id(apartment.getId())
                .latitude(apartment.getLatitude())
                .longitude(apartment.getLongitude())
                .city(apartment.getCity())
                .street(apartment.getStreet())
                .streetNumber(apartment.getStreetNumber())
                .apartmentNumber(apartment.getApartmentNumber())
                .customerId(apartment.getCustomerId())
                .reservationsIds(apartment.getReservationsIds()).build();
    }

    public String mapToJsonObject(ApartmentDto apartment) {
        log.info("Map ApartmentDto to JsonObject");
        Gson gson = new Gson();
        return gson.toJson(apartment);
    }
}
