package com.simol.splearn.domain;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Objects;
import java.util.regex.Pattern;

@Getter
public class Member {
    private Email email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    private Member() {

    }

    public static Member create(MemberCreateRequest memberCreateRequest, PasswordEncoder passwordEncoder) {
        Member member = new Member();

        member.email = new Email(memberCreateRequest.email());
        member.nickname = Objects.requireNonNull(memberCreateRequest.nickname());
        member.passwordHash = Objects.requireNonNull(passwordEncoder.encode(memberCreateRequest.password()));
        member.status = MemberStatus.PENDING;

        return member;
    }

    public void activate() {
        Assert.state(this.status == MemberStatus.PENDING, "PENDING 상태가 아닙니다");
        this.status = MemberStatus.ACTIVATE;
    }

    public void deactivate() {
        Assert.state(this.status == MemberStatus.ACTIVATE, "ACTIVE 상태가 아닙니다");
        this.status = MemberStatus.DEACTIVATED;
    }

    public boolean verifyPassword(String secret, PasswordEncoder passwordEncoder) {
        return passwordEncoder.matches(secret, this.passwordHash);
    }

    public void changeNickname(String nickname) {
        this.nickname = Objects.requireNonNull(nickname);
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(Objects.requireNonNull(password));
    }

    public boolean isActive() {
        return this.status == MemberStatus.ACTIVATE;
    }
}
