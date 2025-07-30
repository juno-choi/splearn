package com.simol.splearn.adapter.integration;

import com.simol.splearn.domain.shared.Email;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

class DummyEmailSenderTest {

    @Test
    @StdIo
    void dummyEmailSender(StdOut out) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();
        dummyEmailSender.send(new Email("juno@mail.com"), "subject", "body");

        Assertions.assertThat(out.capturedLines()[0]).isEqualTo("dummy send email: Email[address=juno@mail.com]");

    }
}