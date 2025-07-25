package com.simol.splearn.application.provided;

import com.simol.splearn.SplearnTestConfiguration;
import com.simol.splearn.domain.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(SplearnTestConfiguration.class)
// test configuration 처리
public class MemberRegisterTest {
    @Autowired
    private MemberRegister memberRegister;

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        Assertions.assertThat(member.getId()).isNotNull();
        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

}
