package com.simol.splearn.application;

import com.simol.splearn.application.provided.MemberRegister;
import com.simol.splearn.application.required.EmailSender;
import com.simol.splearn.application.required.MemberRepository;
import com.simol.splearn.domain.Member;
import com.simol.splearn.domain.MemberRegisterRequest;
import com.simol.splearn.domain.PasswordEncoder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Member register(MemberRegisterRequest memberRegisterRequest) {
        // check

        // domain model
        Member member = Member.register(memberRegisterRequest, passwordEncoder);
        // repository
        memberRepository.save(member);
        // post process
        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");

        return member;
    }
}
