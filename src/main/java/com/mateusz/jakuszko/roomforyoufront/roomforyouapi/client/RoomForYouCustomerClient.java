package com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client;

import com.mateusz.jakuszko.roomforyoufront.dto.CustomerDto;
import com.mateusz.jakuszko.roomforyoufront.mapper.facade.CustomerMapperFacade;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.config.RoomForYouApiConfig;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response.ApartmentResponse;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomForYouCustomerClient {
    private final CustomerMapperFacade customerMapper;
    private final RoomForYouApiConfig roomForYouApiConfig;
    private final RestTemplate restTemplate;

    public List<CustomerDto> getCustomersResponse() {
        StringBuilder url = new StringBuilder();
        url.append(roomForYouApiConfig.getUrl()).append(roomForYouApiConfig.getVersion()).append(roomForYouApiConfig.getCustomer());
        return customerMapper.mapToCustomerDtos(getCustomerResponseList(url.toString()));
    }

    public CustomerDto postForCustomer(CustomerDto customerDto) {
        StringBuilder url = new StringBuilder();
        url.append(roomForYouApiConfig.getUrl()).append(roomForYouApiConfig.getVersion()).append(roomForYouApiConfig.getCustomer());
        return customerMapper.mapToCustomerDto(restTemplate.postForObject(url.toString(), customerDto, CustomerResponse.class));
    }

    public void deleteCustomer(Long id) {
        StringBuilder url = new StringBuilder();
        url.append(roomForYouApiConfig.getUrl()).append(roomForYouApiConfig.getVersion()).append(roomForYouApiConfig.getCustomer())
                .append(id);
        restTemplate.delete(url.toString());
    }

    private List<CustomerResponse> getCustomerResponseList(String url) {
        CustomerResponse[] customers = restTemplate.getForObject(url, CustomerResponse[].class);
        if (customers != null) {
            return Arrays.asList(customers);
        }
        return new ArrayList<>();
    }


}
