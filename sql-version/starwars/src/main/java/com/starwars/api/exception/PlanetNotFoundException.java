package com.starwars.api.exception;

import org.springframework.http.HttpStatus;

import java.util.function.Supplier;

public class PlanetNotFoundException extends Exception {

    private HttpStatus httpStatus;

    public PlanetNotFoundException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public PlanetNotFoundException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}