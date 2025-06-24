package com.simol.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void activateFail1() {
        Member member = new Member("<EMAIL>", "test", "test");
        member.activate();

        Assertions.assertThatThrownBy(() -> {
            member.activate();
        }).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void activateSuccess() {
        Member member = new Member("<EMAIL>", "test", "test");
        member.activate();

        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVATE);
    }

    @Test
    void deactivateFail1() {
        Member member = new Member("<EMAIL>", "test", "test");

        Assertions.assertThatThrownBy(() -> {
            member.deactivate();
        }).isInstanceOf(IllegalStateException.class);

    }
}