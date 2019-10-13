package com.starwars.api.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Configuration
public class RestTemplateConfig {

    public static final String USER_AGENT = "user-agent";
    public static final String PARAMETERS = "parameters";

    @Value("${starwars.user-agent}")
    String userAgent;

    @Bean
    RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate;
    }

    @Bean
    HttpEntity entity() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add(USER_AGENT, userAgent);
        HttpEntity<String> entity = new HttpEntity<String>(PARAMETERS, headers);
        return entity;
    }
}