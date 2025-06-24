package com.simol.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {
    // fixture
    Member member;

    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = new PasswordEncoder() {
            @Override
            public String encode(String password) {
                return password.toUpperCase();
            }

            @Override
            public boolean matches(String password, String passwordHash) {
                return encode(password).equals(passwordHash);
            }
        };
        member = Member.create("mail@mail.com", "juno", "secret", passwordEncoder);
    }

    @Test
    void activateFail1() {
        member.activate();

        Assertions.assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void activateSuccess() {
        member.activate();

        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVATE);
    }

    @Test
    void deactivateFail1() {
        Assertions.assertThatThrownBy(() -> {
            member.deactivate();
        }).isInstanceOf(IllegalStateException.class);

    }

    @Test
    void verifyPassword() {
        Assertions.assertThat(member.verifyPassword("secret", passwordEncoder)).isTrue();
    }

    @Test
    void changeNickname() {
        Assertions.assertThat(member.getNickname()).isEqualTo("juno");

        member.changeNickname("junho");

        Assertions.assertThat(member.getNickname()).isEqualTo("junho");
    }

    @Test
    void changePassword() {
        String newPassword = "verysecret";

        member.changePassword(newPassword, passwordEncoder);

        Assertions.assertThat(member.verifyPassword(newPassword, passwordEncoder)).isTrue();
    }
}