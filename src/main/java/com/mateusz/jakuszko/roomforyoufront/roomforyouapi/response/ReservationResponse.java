package com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "startDate",
        "endDate",
        "apartmentId",
        "userId"
})
@Getter
public class ReservationResponse {
    @JsonProperty("id")
    public Long id;
    @JsonProperty("startDate")
    public LocalDate startDate;
    @JsonProperty("endDate")
    public LocalDate endDate;
    @JsonProperty("apartmentId")
    public Long apartmentId;
    @JsonProperty("userId")
    public Long userId;
}
