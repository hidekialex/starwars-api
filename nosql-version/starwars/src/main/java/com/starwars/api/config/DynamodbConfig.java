package com.starwars.api.config;

import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.starwars.api.property.DynamodbProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DynamodbConfig {

    public static final String PLANET = "Planet";

    private DynamodbProperties properties;

    DynamodbConfig(DynamodbProperties properties) {
        this.properties = properties;
    }

    @Bean
    AmazonDynamoDB dynamoDB() {
        final AwsClientBuilder.EndpointConfiguration endpoint = new AwsClientBuilder
                .EndpointConfiguration(properties.getEndpoint(), Regions.US_EAST_1.getName());
        AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(endpoint)
                .build();
        return client;
    }

    @Bean
    Table getTable() {
        DynamoDB dynamoDB = new DynamoDB(dynamoDB());
        return dynamoDB.getTable(PLANET);
    }


    @Bean
    DynamoDBMapperConfig config() {
        final DynamoDBMapperConfig.Builder builder = new DynamoDBMapperConfig.Builder();
        builder.setConsistentReads(DynamoDBMapperConfig.ConsistentReads.EVENTUAL);
        return builder.build();
    }

    @Bean
    DynamoDBMapper mapper() {
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB(), config());
        return mapper;
    }
}