package com.starwars.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlanetsResponse {

    @JsonProperty("totalElements")
    private Long totalElements;

    @JsonProperty("page")
    private Integer page;

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("totalPages")
    private Integer totalPages;

    @JsonProperty("planets")
    private List<PlanetInfoResponse> planets;
}
