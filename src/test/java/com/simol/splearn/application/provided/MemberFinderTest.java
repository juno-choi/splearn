package com.simol.splearn.application.provided;

import com.simol.splearn.SplearnTestConfiguration;
import com.simol.splearn.domain.Member;
import com.simol.splearn.domain.MemberFixture;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Import(SplearnTestConfiguration.class) // test configuration 처리
class MemberFinderTest {

    @Autowired
    private MemberFinder memberFinder;

    @Autowired
    private MemberRegister memberRegister;

    @Autowired
    private EntityManager entityManager;

    @Test
    void find() {
        Member member = memberRegister.register(MemberFixture.createMemberRegisterRequest());
        entityManager.flush();
        entityManager.clear();

        Member findMember = memberFinder.find(member.getId());

        Assertions.assertThat(member.getId()).isEqualTo(findMember.getId());
    }

    @Test
    void findFail() {
        Assertions.assertThatThrownBy(() -> memberFinder.find(999L))
                .isInstanceOf(IllegalArgumentException.class);
    }
}