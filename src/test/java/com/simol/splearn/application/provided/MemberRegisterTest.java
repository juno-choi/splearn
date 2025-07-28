package com.simol.splearn.application.provided;

import com.simol.splearn.SplearnTestConfiguration;
import com.simol.splearn.domain.*;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestConstructor;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class) // test configuration 처리
//@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class MemberRegisterTest {

    @Autowired
    private MemberRegister memberRegister;

    @Autowired
    private EntityManager entityManager;

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

    @Test
    void memberRegisterRequestFail() {
        extracted(new MemberRegisterRequest("juno@mail.com", "juno", "verysecret"));
        extracted(new MemberRegisterRequest("juno@mail.com", "juno12345678901234567890", "verysecret"));
        extracted(new MemberRegisterRequest("juno.mail.com", "juno12345678901234567890", "verysecret"));

    }

    private void extracted(MemberRegisterRequest invalid) {
        Assertions.assertThatThrownBy(() -> memberRegister.register(invalid))
                .isInstanceOf(ConstraintViolationException.class);
    }

    @Test
    void activate() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());

        // repository 반영 후 activate 실행되도록
        entityManager.flush();
        entityManager.clear();

        member = memberRegister.activate(member.getId());
        entityManager.flush();

        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.ACTIVATE);
    }
}
