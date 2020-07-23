package com.mateusz.jakuszko.roomforyoufront.roomforyouapi.client;

import com.mateusz.jakuszko.roomforyoufront.dto.CustomerDto;
import com.mateusz.jakuszko.roomforyoufront.mapper.facade.CustomerMapperFacade;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.config.RoomForYouApiConfig;
import com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RoomForYouCustomerApiClient {
    private final CustomerMapperFacade customerMapper;
    private final RoomForYouApiConfig roomForYouApiConfig;
    private final RestTemplate restTemplate;

    public List<CustomerDto> getCustomersResponse() {
        return customerMapper.mapToCustomerDtos(getCustomerResponseList(buildBasicUrl().toString()));
    }

    public CustomerDto postForCustomer(CustomerDto customerDto) {
        return customerMapper.mapToCustomerDto(restTemplate.postForObject(buildBasicUrl().toString(),
                customerDto, CustomerResponse.class));
    }

    public void deleteCustomer(Long id) {
        restTemplate.delete(buildBasicUrl().append(id).toString());
    }

    private List<CustomerResponse> getCustomerResponseList(String url) {
        CustomerResponse[] customers = restTemplate.getForObject(url, CustomerResponse[].class);
        if (customers != null) {
            return Arrays.asList(customers);
        }
        return new ArrayList<>();
    }

    private StringBuilder buildBasicUrl() {
        return new StringBuilder().append(roomForYouApiConfig.getUrl())
                .append(roomForYouApiConfig.getVersion())
                .append(roomForYouApiConfig.getCustomer());
    }
}
