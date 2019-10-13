package com.starwars.api.controller.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class CreatePlanetRequest {

    @NotBlank
    private String name;
}
