package com.mateusz.jakuszko.roomforyoufront.mapper.facade;

import com.mateusz.jakuszko.roomforyoufront.dto.CustomerDto;
import com.mateusz.jakuszko.roomforyoufront.mapper.ApartmentMapper;
import com.mateusz.jakuszko.roomforyoufront.mapper.CustomerMapper;
import com.mateusz.jakuszko.roomforyoufront.mapper.ReservationMapper;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerMapperFacade {
    private final CustomerMapper customerMapper;
    private final ReservationMapper reservationMapper;
    private final ApartmentMapper apartmentMapper;

    public CustomerDto mapToCustomerDto(CustomerResponse customerResponse) {
        return customerMapper.mapToCustomerDto(customerResponse, apartmentMapper.mapToApartmentDtos(customerResponse.getApartments()),
                reservationMapper.mapToReservationDtos(customerResponse.getReservations()));
    }

    public List<CustomerDto> mapToCustomerDtos(List<CustomerResponse> customerResponses) {
        return customerResponses.stream()
                .map(response -> CustomerDto.builder()
                        .id(response.getId())
                        .name(response.getName())
                        .username(response.getUsername())
                        .surname(response.getSurname())
                        .email(response.getEmail())
                        .password(response.getPassword())
                        .role(response.getRole())
                        .apartments(apartmentMapper.mapToApartmentDtos(response.getApartments()))
                        .reservations(reservationMapper.mapToReservationDtos(response.getReservations())).build())
                .collect(Collectors.toList());
    }
}
