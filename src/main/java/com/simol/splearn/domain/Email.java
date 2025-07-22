package com.simol.splearn.domain;

import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public record Email(String address) {

    private static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");

    public Email {
        if (!emailPattern.matcher(address).matches()) {
            throw new IllegalArgumentException("Invalid email format : %s".formatted(address));
        }
    }
}
