package com.simol.splearn.application.member.provided;

import com.simol.splearn.domain.member.Member;
import com.simol.splearn.domain.member.MemberRegisterRequest;
import jakarta.validation.Valid;

/**
 * 회원의 등록과 관련된 기능을 제공
 */
public interface MemberRegister {
    Member register(@Valid MemberRegisterRequest memberRegisterRequest);
    Member activate(Long memberId);
}
