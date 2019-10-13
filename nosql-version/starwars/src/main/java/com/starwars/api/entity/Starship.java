package com.starwars.api.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.starwars.api.caller.dto.StarshipDTO;
import lombok.Data;

@Data
@DynamoDBDocument
@JsonIgnoreProperties(ignoreUnknown = true)
public class Starship {

    @DynamoDBAttribute(attributeName = "name")
    private String name;

    @DynamoDBAttribute(attributeName = "model")
    private String model;

    @DynamoDBAttribute(attributeName = "manufacturer")
    private String manufacturer;

    public static Starship of(StarshipDTO dto) {
        Starship starship = new Starship();
        starship.setManufacturer(dto.getManufacturer());
        starship.setModel(dto.getModel());
        starship.setName(dto.getName());
        return starship;
    }
}
