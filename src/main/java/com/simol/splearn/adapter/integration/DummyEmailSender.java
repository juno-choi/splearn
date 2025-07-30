package com.simol.splearn.adapter.integration;

import com.simol.splearn.application.member.required.EmailSender;
import com.simol.splearn.domain.shared.Email;
import org.springframework.context.annotation.Fallback;
import org.springframework.stereotype.Component;

@Component
// spring 6.2에 추가된 기능으로 bean을 모두 검색하다가 등록된 bean이 없다면 @Fallback 을 추가해서 사용한다.
@Fallback
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("dummy send email: " + email);
    }
}
