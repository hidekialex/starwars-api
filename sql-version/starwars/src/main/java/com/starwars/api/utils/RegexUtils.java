package com.starwars.api.utils;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

    private static String REGEX = "([0-9]+)";

    public static Optional<Integer> extractIdForUrl(String url) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(url);
        if(matcher.find()) {
            return Optional.of(Integer.parseInt(matcher.group()));
        }
        return Optional.empty();
    }
}
