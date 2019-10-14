package com.starwars.api.caller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class PlanetDetailDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("climate")
    private String climate;

    @JsonProperty("terrain")
    private String terrain;

    @JsonProperty("url")
    private String url;

    @JsonProperty("films")
    private List<String> films;
}
