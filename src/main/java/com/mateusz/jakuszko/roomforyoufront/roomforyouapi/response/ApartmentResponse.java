package com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "latitude",
        "longitude",
        "city",
        "street",
        "streetNumber",
        "apartmentNumber",
        "reservationsIds",
        "customerId"
})
@Getter
public class ApartmentResponse {
    @JsonProperty("id")
    public Long id;
    @JsonProperty("latitude")
    public Double latitude;
    @JsonProperty("longitude")
    public Double longitude;
    @JsonProperty("city")
    public String city;
    @JsonProperty("street")
    public String street;
    @JsonProperty("streetNumber")
    public String streetNumber;
    @JsonProperty("apartmentNumber")
    public Integer apartmentNumber;
    @JsonProperty("reservationsIds")
    public List<Long> reservationsIds = null;
    @JsonProperty("customerId")
    public Long customerId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

}
