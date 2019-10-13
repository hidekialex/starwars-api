package com.starwars.api.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIndexHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.starwars.api.caller.dto.PlanetDetailDTO;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@DynamoDBTable(tableName = "Planet")
public class Planet {

    @DynamoDBHashKey(attributeName = "id")
    private String id;

    @DynamoDBIndexHashKey(attributeName = "name", globalSecondaryIndexNames = "nome-index")
    private String name;

    @DynamoDBAttribute(attributeName = "climate")
    private String climate;

    @DynamoDBAttribute(attributeName = "terrain")
    private String terrain;

    @DynamoDBAttribute(attributeName = "films")
    private List<Film> films;

    public static Planet of(PlanetDetailDTO dto, List<Film> films) {
        Planet planet = new Planet();
        planet.setId(UUID.randomUUID().toString());
        planet.setClimate(dto.getClimate());
        planet.setName(dto.getName());
        planet.setTerrain(dto.getTerrain());
        planet.setFilms(films);
        return planet;
    }
}