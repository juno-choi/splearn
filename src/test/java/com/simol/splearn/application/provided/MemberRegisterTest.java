package com.simol.splearn.application.provided;

import com.simol.splearn.SplearnTestConfiguration;
import com.simol.splearn.domain.*;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class) // test configuration 처리
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public record MemberRegisterTest(MemberRegister memberRegister) {

    @Test
    void register() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        Assertions.assertThat(member.getId()).isNotNull();
        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }
    @Test
    void duplicateEmailFail() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        Assertions.assertThatThrownBy(() -> {
            memberRegister.register(MemberFixture.createMemberRegisterRequest());
        }).isInstanceOf(DuplicateEmailException.class);


    }

}
