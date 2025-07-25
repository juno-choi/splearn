package com.simol.splearn.adapter.integration;

import com.simol.splearn.application.required.EmailSender;
import com.simol.splearn.domain.Email;
import org.springframework.stereotype.Component;

@Component
public class DummyEmailSender implements EmailSender {
    @Override
    public void send(Email email, String subject, String body) {
        System.out.println("dummy send email: " + email);
    }
}
