package com.starwars.api.caller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class StarshipDTO {

    @JsonProperty("name")
    private String name;

    @JsonProperty("model")
    private String model;

    @JsonProperty("manufacturer")
    private String manufacturer;

    @JsonProperty("url")
    private String url;
}