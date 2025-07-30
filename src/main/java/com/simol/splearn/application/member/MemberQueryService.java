package com.simol.splearn.application.member;

import com.simol.splearn.application.member.provided.MemberFinder;
import com.simol.splearn.application.member.required.MemberRepository;
import com.simol.splearn.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class MemberQueryService implements MemberFinder {
    private final MemberRepository memberRepository;

    @Override
    public Member find(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("member not found id: %s".formatted(memberId)));
    }
}
