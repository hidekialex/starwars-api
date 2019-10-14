package com.starwars.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.function.Supplier;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PlanetNotFoundException extends Exception {

    public PlanetNotFoundException(String message) {
        super(message);
    }
}