package com.simol.splearn.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;


class EmailTest {

    @Test
    void equality() {
        // record가 아닌 class로 만들었다면 hash와 equals를 구현하여 값 객체의 동등성을 체크해야 한다.
        var email1 = new Email("test@test.com");
        var email2 = new Email("test@test.com");

        Assertions.assertThat(email1).isEqualTo(email2);
    }

}