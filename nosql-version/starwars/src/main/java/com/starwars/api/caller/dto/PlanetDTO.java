package com.starwars.api.caller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlanetDTO {

    @JsonProperty("results")
    private List<PlanetDetailDTO> results;
}