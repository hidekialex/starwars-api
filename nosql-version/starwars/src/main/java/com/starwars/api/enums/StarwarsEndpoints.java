package com.starwars.api.enums;

public enum StarwarsEndpoints {

    GET_PLANETS_BY_NAME("/planets?search={name}");

    private String url;

    StarwarsEndpoints(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
