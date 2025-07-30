package com.simol.splearn.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.simol.splearn.domain.member.MemberFixture.createMemberRegisterRequest;
import static com.simol.splearn.domain.member.MemberFixture.createPasswordEncoder;

class MemberTest {
    private static final Logger log = LoggerFactory.getLogger(MemberTest.class);
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
        Assertions.assertThat(member.getDetail().getActivatedAt()).isNotNull();
    }
    @Test
    void deactivateSuccess() {
        member.activate();
        member.deactivate();

        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.DEACTIVATED);
        Assertions.assertThat(member.getDetail().getDeactivatedAt()).isNotNull();
    }

    @Test
    void deactivateFail1() {
        Assertions.assertThatThrownBy(() -> {
            member.deactivate();
        }).isInstanceOf(IllegalStateException.class);

    }

    @Test
    void verifyPassword() {
        Assertions.assertThat(member.verifyPassword("verysecret", passwordEncoder)).isTrue();
    }

    @Test
    void changeNickname() {
        Assertions.assertThat(member.getNickname()).isEqualTo("juno123");

        member.changeNickname("junho");

        Assertions.assertThat(member.getNickname()).isEqualTo("junho");
    }

    @Test
    void changePassword() {
        String newPassword = "verysecret2";

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

    @Test
    void registerMember() {
        Assertions.assertThat(member.getStatus()).isEqualTo(MemberStatus.PENDING);
        Assertions.assertThat(member.getDetail().getRegisteredAt()).isNotNull();
    }

    @Test
    void updateInfo() {
        member.activate();

        MemberInfoUpdateRequest request = new MemberInfoUpdateRequest("choi", "junono", "자기소개");
        member.updateInfo(request);

        Assertions.assertThat(member.getNickname()).isEqualTo(request.nickname());
        Assertions.assertThat(member.getDetail().getProfile().address()).isEqualTo(request.profileAddress());
        Assertions.assertThat(member.getDetail().getIntroduction()).isEqualTo(request.introduction());

    }

    @Test
    void updateInfoFail() {
        Assertions.assertThatThrownBy(() -> {
            MemberInfoUpdateRequest request = new MemberInfoUpdateRequest("choi", "junono", "자기소개");
            member.updateInfo(request);
        }).isInstanceOf(IllegalStateException.class);
    }
}