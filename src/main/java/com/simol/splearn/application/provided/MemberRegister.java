package com.simol.splearn.application.provided;

import com.simol.splearn.domain.Member;
import com.simol.splearn.domain.MemberRegisterRequest;
import jakarta.validation.Valid;

/**
 * 회원의 등록과 관련된 기능을 제공
 */
public interface MemberRegister {
    Member register(@Valid MemberRegisterRequest memberRegisterRequest);
}
