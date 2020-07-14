package com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "startDate",
        "endDate",
        "apartmentId",
        "userId"
})
public class ReservationResponse {
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("startDate")
    public String startDate;
    @JsonProperty("endDate")
    public String endDate;
    @JsonProperty("apartmentId")
    public Integer apartmentId;
    @JsonProperty("userId")
    public Integer userId;
}
