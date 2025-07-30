package com.simol.splearn.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProfileTest {

    @Test
    void profile() {
        new Profile("test");
    }

    @Test
    void profileFail() {
        Assertions.assertThatThrownBy(() -> new Profile("")).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new Profile("12345678901234567890")).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new Profile("A")).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> new Profile("프로필")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void url() {
        Profile profile = new Profile("test");
        Assertions.assertThat(profile.url()).contains("@");
    }
}