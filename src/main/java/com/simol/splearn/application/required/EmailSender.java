package com.simol.splearn.application.required;

import com.simol.splearn.domain.Email;

/**
 * 이메일을 발송한다
 */
public interface EmailSender {
    void send(Email email, String subject, String body);
}
