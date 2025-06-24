package com.simol.splearn.domain;

import lombok.Getter;
import org.springframework.util.Assert;

import java.util.Objects;

@Getter
public class Member {
    private String email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    private Member(String email, String nickname, String passwordHash) {
        this.email = Objects.requireNonNull(email);
        this.nickname = Objects.requireNonNull(nickname);
        this.passwordHash = Objects.requireNonNull(passwordHash);
        this.status = MemberStatus.PENDING;
    }

    public static Member create(String email, String nickname, String password, PasswordEncoder passwordEncoder) {
        return new Member(email, nickname, passwordEncoder.encode(password));
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
        this.nickname = nickname;
    }

    public void changePassword(String password, PasswordEncoder passwordEncoder) {
        this.passwordHash = passwordEncoder.encode(password);
    }
}
