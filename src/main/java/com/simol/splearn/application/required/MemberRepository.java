package com.simol.splearn.application.required;

import com.simol.splearn.domain.Member;
import org.springframework.data.repository.Repository;

/**
 * 회원 정보를 저장하거나 조회
 */
public interface MemberRepository extends Repository<Member, Long> {
    Member save(Member member);
}
