package com.simol.splearn;

import com.simol.splearn.application.member.required.EmailSender;
import com.simol.splearn.domain.member.MemberFixture;
import com.simol.splearn.domain.member.PasswordEncoder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
// test code에서만 작동하는 bean
public class SplearnTestConfiguration {
    @Bean
    public EmailSender emailSender() {
        return (email, subject, body) -> System.out.println("sending email: " + email);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return MemberFixture.createPasswordEncoder();
    }
}
