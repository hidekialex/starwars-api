package com.starwars.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlanetsResponse {

    @JsonProperty("planets")
    private List<PlanetInfoResponse> planets;

    public static PlanetsResponse of(List<PlanetInfoResponse> planetsInfoResponse) {
        PlanetsResponse response = new PlanetsResponse();
        response.setPlanets(planetsInfoResponse);
        return response;
    }
}
