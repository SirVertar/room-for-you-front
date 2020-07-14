package com.mateusz.jakuszko.roomforyoufront.mapper;

import com.mateusz.jakuszko.roomforyoufront.dto.ApartmentDto;
import com.mateusz.jakuszko.roomforyoufront.dto.CustomerDto;
import com.mateusz.jakuszko.roomforyoufront.dto.ReservationDto;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response.CustomerResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class CustomerMapper {
    public CustomerDto mapToCustomerDto(CustomerResponse customerResponse, List<ApartmentDto> apartments,
                                        List<ReservationDto> reservations){
        log.info("Map CustomerResponse to CustomerDto");
        return CustomerDto.builder()
                .id(customerResponse.getId())
                .name(customerResponse.getName())
                .surname(customerResponse.getSurname())
                .email(customerResponse.getEmail())
                .password(customerResponse.getPassword())
                .role(customerResponse.getRole())
                .apartments(apartments)
                .reservations(reservations).build();
    }
}
