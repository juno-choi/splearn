package com.simol.splearn.application.member;

import com.simol.splearn.application.member.provided.MemberFinder;
import com.simol.splearn.application.member.provided.MemberRegister;
import com.simol.splearn.application.member.required.EmailSender;
import com.simol.splearn.application.member.required.MemberRepository;
import com.simol.splearn.domain.member.*;
import com.simol.splearn.domain.shared.Email;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
@RequiredArgsConstructor
@Transactional
public class MemberModifyService implements MemberRegister {
    private final MemberRepository memberRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;
    private final MemberFinder memberFinder;

    @Override
    public Member register(MemberRegisterRequest memberRegisterRequest) {
        // check
        checkDuplicateEmail(memberRegisterRequest);
        // domain model
        Member member = Member.register(memberRegisterRequest, passwordEncoder);
        // repository
        memberRepository.save(member);
        // post process
        sendWelcomeEmail(member);

        return member;
    }

    @Override
    public Member activate(Long memberId) {
        // find
        Member member = memberFinder.find(memberId);
        // logic
        member.activate();

        return memberRepository.save(member);
    }

    @Override
    public Member deactivate(Long memberId) {
        // find
        Member member = memberFinder.find(memberId);
        // logic
        member.deactivate();

        return memberRepository.save(member);
    }

    @Override
    public Member updateInfo(Long memberId, MemberInfoUpdateRequest memberInfoUpdateRequest) {
        Member member = memberFinder.find(memberId);

        member.updateInfo(memberInfoUpdateRequest);

        return memberRepository.save(member);
    }

    private void sendWelcomeEmail(Member member) {
        emailSender.send(member.getEmail(), "등록을 완료해주세요", "아래 링크를 클릭해서 등록을 완료해주세요");
    }

    private void checkDuplicateEmail(MemberRegisterRequest memberRegisterRequest) {
        if (memberRepository.findByEmail(new Email(memberRegisterRequest.email())).isPresent()) {
            throw new DuplicateEmailException("duplicated email!");
        }
    }
}
