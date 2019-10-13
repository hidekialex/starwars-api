package com.starwars.api.repository.impl;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.*;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import com.starwars.api.entity.PagedPlanets;
import com.starwars.api.entity.Planet;
import com.starwars.api.repository.PlanetRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class PlanetRepositoryImpl implements PlanetRepository {

    public static final String NAME_INDEX = "name-index";
    public static final String NAME = "name";
    public static final String ID = "id";

    private DynamoDBMapper mapper;

    private AmazonDynamoDB client;

    private Table table;

    PlanetRepositoryImpl(DynamoDBMapper mapper, AmazonDynamoDB client, Table table) {
        this.mapper = mapper;
        this.client = client;
        this.table = table;
    }

    @Override
    public void create(Planet planet) {
        mapper.save(planet);
    }

    @Override
    public Optional<Planet> getById(String id) {
        return Optional.ofNullable(mapper.load(Planet.class, id));
    }

    @Override
    public Optional<Planet> getByName(String name) {
        Index index = table.getIndex(NAME_INDEX);
        ItemCollection<QueryOutcome> outcome = index.query(NAME, name, null, null, ID, null, null);
        if (!outcome.iterator().hasNext()) {
            return Optional.empty();
        }
        Item item = outcome.iterator().next();
        String id = item.getString(ID);
        return Optional.ofNullable(mapper.load(Planet.class, id));
    }

    @Override
    public void remove(String id) {
        Planet planet = new Planet();
        planet.setId(id);
        mapper.delete(planet);
    }

    @Override
    public PagedPlanets getPlanets(Integer size, Optional<String> startKey) {
        ScanRequest scanRequest = new ScanRequest().withTableName(table.getTableName()).withLimit(size);
        if (startKey.isPresent()) {
            Map<String, AttributeValue> key = new HashMap<>();
            key.put(ID, new AttributeValue().withS(startKey.get()));
            scanRequest.withExclusiveStartKey(key);
        }
        ScanResult scan = client.scan(scanRequest);

        PagedPlanets pagedPlanets = new PagedPlanets();
        if (scan.getLastEvaluatedKey() != null && scan.getLastEvaluatedKey().size() > 0) {
            String id = scan.getLastEvaluatedKey().get(ID).getS();
            pagedPlanets.setLastKey(id);
        }

        List<Planet> planets = new ArrayList<>();
        for (Map<String, AttributeValue> planetItem : scan.getItems()) {
            Planet planet = mapper.load(Planet.class, planetItem.get(ID).getS());
            planets.add(planet);
        }
        pagedPlanets.setPlanets(planets);
        return pagedPlanets;
    }
}