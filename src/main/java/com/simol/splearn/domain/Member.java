package com.simol.splearn.domain;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class Member {
    private String email;

    private String nickname;

    private String passwordHash;

    private MemberStatus status;

    public Member(String email, String nickname, String passwordHash) {
        this.email = email;
        this.nickname = nickname;
        this.passwordHash = passwordHash;
        this.status = MemberStatus.PENDING;
    }

    public void activate() {
        Assert.state(this.status == MemberStatus.PENDING, "PENDING 상태가 아닙니다");
        this.status = MemberStatus.ACTIVATE;
    }

    public void deactivate() {
        Assert.state(this.status == MemberStatus.ACTIVATE, "ACTIVE 상태가 아닙니다");
        this.status = MemberStatus.DEACTIVATED;
    }
}
