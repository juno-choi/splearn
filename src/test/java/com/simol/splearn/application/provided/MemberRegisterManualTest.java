package com.simol.splearn.application.provided;

import com.simol.splearn.application.MemberService;
import com.simol.splearn.application.required.EmailSender;
import com.simol.splearn.application.required.MemberRepository;
import com.simol.splearn.domain.Email;
import com.simol.splearn.domain.Member;
import com.simol.splearn.domain.MemberFixture;
import com.simol.splearn.domain.MemberStatus;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

class MemberRegisterManualTest {

    // stub은 단순히 테스트를 진행하기 위해 속이는 역할
    @Test
    void registerTestStub() {
        MemberRegister register = new MemberService(
                new MemberRepositoryStub(), new EmailSenderStub(), MemberFixture.createPasswordEncoder()
        );

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        Assertions.assertThat(member.getId()).isNotNull();
        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
    }

    // mock은 테스트를 진행하기 위해 속이는 역할 + 실행이 되었는지 확인하는 로직도 포함
    @Test
    void registerTestMock() {
        EmailSenderMock emailSenderMock = new EmailSenderMock();

        MemberRegister register = new MemberService(
                new MemberRepositoryStub(), emailSenderMock, MemberFixture.createPasswordEncoder()
        );

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        Assertions.assertThat(member.getId()).isNotNull();
        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        Assertions.assertThat(emailSenderMock.tos.size()).isEqualTo(1);
        Assertions.assertThat(emailSenderMock.tos.getFirst()).isEqualTo(member.getEmail());
    }

    // spring test mockito 활용하여 mock을 자동 구현
    @Test
    void registerTestMockito() {
        EmailSenderMock emailSenderMock = Mockito.mock(EmailSenderMock.class);

        MemberRegister register = new MemberService(
                new MemberRepositoryStub(), emailSenderMock, MemberFixture.createPasswordEncoder()
        );

        Member member = register.register(MemberFixture.createMemberRegisterRequest());

        Assertions.assertThat(member.getId()).isNotNull();
        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);

        Mockito.verify(emailSenderMock).send(eq(member.getEmail()), any(), any());

    }

    static class MemberRepositoryStub implements MemberRepository {

        @Override
        public Member save(Member member) {
            // test 코드에서 reflection을 이용하여 간단하게 setId 적용
            ReflectionTestUtils.setField(member, "id", 1L);
            return member;
        }

        @Override
        public Optional<Member> findByEmail(Email email) {
            return Optional.empty();
        }
    }

    static class EmailSenderStub implements EmailSender {

        @Override
        public void send(Email email, String subject, String body) {

        }
    }

    static class EmailSenderMock implements EmailSender {

        List<Email> tos = new ArrayList<>();

        @Override
        public void send(Email email, String subject, String body) {
            tos.add(email);
        }
    }
}