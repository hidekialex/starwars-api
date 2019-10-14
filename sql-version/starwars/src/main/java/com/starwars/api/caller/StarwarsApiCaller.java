package com.starwars.api.caller;

import com.starwars.api.caller.dto.PlanetDTO;
import com.starwars.api.caller.dto.PlanetDetailDTO;
import com.starwars.api.exception.PlanetNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.CompletableFuture;

import static com.starwars.api.enums.StarwarsEndpoints.GET_PLANETS_BY_NAME;

@Component
public class StarwarsApiCaller {

    @Value("${starwars.url}")
    private String url;

    private RestTemplate restTemplate;

    private HttpEntity entity;

    StarwarsApiCaller(RestTemplate restTemplate, HttpEntity entity) {
        this.restTemplate = restTemplate;
        this.entity = entity;
    }

    public PlanetDetailDTO getPlanetByName(String name) throws PlanetNotFoundException {
        ResponseEntity<PlanetDTO> response = restTemplate.exchange(url.concat(GET_PLANETS_BY_NAME.getUrl()), HttpMethod.GET, entity, PlanetDTO.class, name);
        return response.getBody()
                .getResults()
                .stream()
                .findFirst()
                .orElseThrow(() -> new PlanetNotFoundException("Planet not found."));
    }

    @Async
    public <T> CompletableFuture<T> getSomethingById(String url, Class<T> type) {
        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, type);
        if(!HttpStatus.OK.equals(response.getStatusCode())) {
            return null;
        }
        return CompletableFuture.completedFuture(response.getBody());
    }
}
