package com.simol.splearn.domain.member;

import com.simol.splearn.domain.AbstractEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Getter
@ToString(callSuper = true)
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
@Table(name = "MEMBER_DETAIL", uniqueConstraints = {@UniqueConstraint(name = "UK_MEMBER_PROFILE_ADDRESS", columnNames = "profile_address")})
public class MemberDetail extends AbstractEntity {
    @Embedded
    private Profile profile;

    @Column(columnDefinition = "TEXT")
    private String introduction;

    @Column(nullable = false)
    private LocalDateTime registeredAt;
    private LocalDateTime activatedAt;
    private LocalDateTime deactivatedAt;

    static MemberDetail create() {
        MemberDetail memberDetail = new MemberDetail();
        memberDetail.registeredAt = LocalDateTime.now();
        return memberDetail;
    }

    void setActivatedAt() {
        Assert.isTrue(this.activatedAt == null, "이미 activatedAt 값이 설정되었습니다");
        this.activatedAt = LocalDateTime.now();
    }

    void deactivate() {
        Assert.isTrue(this.deactivatedAt == null, "이미 activatedAt 값이 설정되었습니다");
        this.deactivatedAt = LocalDateTime.now();
    }

    void updateInfo(MemberInfoUpdateRequest updateRequest) {
        this.profile = new Profile(updateRequest.profileAddress());
        this.introduction = Objects.requireNonNull(updateRequest.introduction());
    }
}
