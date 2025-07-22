package com.simol.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.simol.splearn.domain.MemberFixture.createMemberRegisterRequest;
import static com.simol.splearn.domain.MemberFixture.createPasswordEncoder;

class MemberTest {
    // fixture
    Member member;

    PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        this.passwordEncoder = createPasswordEncoder();
        MemberRegisterRequest memberRegisterRequest = createMemberRegisterRequest();
        member = Member.register(memberRegisterRequest, passwordEncoder);
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

    @Test
    void constructorNullFail1() {
        MemberRegisterRequest memberRegisterRequest = new MemberRegisterRequest(null, "juno", "secret");
        Assertions.assertThatThrownBy(() -> {
            Member.register(memberRegisterRequest, passwordEncoder);
        }).isInstanceOf(NullPointerException.class);
    }

    @Test
    void isActive() {
        Assertions.assertThat(member.isActive()).isFalse();

        member.activate();

        Assertions.assertThat(member.isActive()).isTrue();

        member.deactivate();

        Assertions.assertThat(member.isActive()).isFalse();

    }

    @Test
    void invalidEmail() {
        Assertions.assertThatThrownBy(() -> Member.register(createMemberRegisterRequest("invalid email"), passwordEncoder))
                .isInstanceOf(IllegalArgumentException.class);
    }

}