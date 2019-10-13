package com.starwars.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlanetsResponse {

    @JsonProperty("lastKey")
    private String lastKey;

    @JsonProperty("planets")
    private List<PlanetInfoResponse> planets;

    public static PlanetsResponse of(String lastKey, List<PlanetInfoResponse> planetsInfoResponse) {
        PlanetsResponse response = new PlanetsResponse();
        response.setLastKey(lastKey);
        response.setPlanets(planetsInfoResponse);
        return response;
    }
}
