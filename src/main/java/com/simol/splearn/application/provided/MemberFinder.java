package com.simol.splearn.application.provided;

import com.simol.splearn.domain.Member;

/**
 * 회원을 조회한다
 */
public interface MemberFinder {
    Member find(Long memberId);
}
