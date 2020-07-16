package com.mateusz.jakuszko.roomforyoufront.roomforyouapi.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "username",
        "password",
        "name",
        "surname",
        "email",
        "role",
        "reservations",
        "apartmentDtos"
})
@Getter
public class CustomerResponse {
    @JsonProperty("id")
    public Long id;
    @JsonProperty("username")
    public String username;
    @JsonProperty("password")
    public String password;
    @JsonProperty("name")
    public String name;
    @JsonProperty("surname")
    public String surname;
    @JsonProperty("email")
    public String email;
    @JsonProperty("role")
    public String role;
    @JsonProperty("reservations")
    public List<ReservationResponse> reservations;
    @JsonProperty("apartments")
    public List<ApartmentResponse> apartments;
}
