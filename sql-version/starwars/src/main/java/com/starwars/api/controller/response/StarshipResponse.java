package com.starwars.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.starwars.api.entity.Starship;
import lombok.Data;

@Data
public class StarshipResponse {

    @JsonProperty("name")
    private String name;

    @JsonProperty("model")
    private String model;

    @JsonProperty("manufacturer")
    private String manufacturer;

    public static StarshipResponse of(Starship starship) {
        StarshipResponse response = new StarshipResponse();
        response.setManufacturer(starship.getManufacturer());
        response.setModel(starship.getModel());
        response.setName(starship.getName());
        return response;
    }
}
