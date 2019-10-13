package com.starwars.api.exception;

import org.springframework.http.HttpStatus;

public class StarwarsApiException extends Exception {

    private HttpStatus httpStatus;

    public StarwarsApiException(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public StarwarsApiException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
