package com.starwars.api.controller.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RemovePlanetResponse {

    @JsonProperty("msg")
    private String msg;
}
