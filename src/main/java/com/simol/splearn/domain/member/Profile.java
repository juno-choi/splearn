package com.simol.splearn.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

@Embeddable
public record Profile(@Column(length = 20, unique = true) String address) {
    private static final Pattern PROFILE_ADDRESS_PATTERN = Pattern.compile("[a-z0-9]+");

    public Profile {
        if (!PROFILE_ADDRESS_PATTERN.matcher(address).matches()) {
            throw new IllegalArgumentException("Invalid profile format : %s".formatted(address));
        }

        if (address.length() > 15) {
            throw new IllegalArgumentException("Profile address is too long : %s".formatted(address));
        }
    }

    public String url() {
        return "@" + address;
    }
}
