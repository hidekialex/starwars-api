package com.starwars.api.entity;

import lombok.Data;

import java.util.List;

@Data
public class PagedPlanets {

    private String lastKey;

    List<Planet> planets;
}
