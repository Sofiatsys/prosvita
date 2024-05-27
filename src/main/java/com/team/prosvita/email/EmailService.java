package com.team.prosvita.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailService implements IEmailSender {
    private final static Logger LOGGER = LoggerFactory.getLogger(EmailService.class);
    private final JavaMailSender mailSender;
    @Async
    @Override
    public void sendEmailConfirmation(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Email Confirmation");
            helper.setFrom("registration@prosvita.com");

            mailSender.send(mimeMessage);
            LOGGER.info("Email confirmation sent to {}", to);

        } catch (MessagingException e) {
            LOGGER.error("Failed to send email confirmation", e);
            throw new IllegalStateException("Failed to send email confirmation");
        }
    }

    @Async
    @Override
    public void sendPasswordReset(String to, String email) {
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setText(email, true);
            helper.setTo(to);
            helper.setSubject("Password Reset");
            helper.setFrom("registration@prosvita.com");

            mailSender.send(mimeMessage);
            LOGGER.info("Password reset email sent to {}", to);

        } catch (MessagingException e) {
            LOGGER.error("Failed to send password reset email", e);
            throw new IllegalStateException("Failed to send password reset email");
        }
    }
}
