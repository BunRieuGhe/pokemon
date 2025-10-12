package com.alexpauv.pokemon.service;

import com.alexpauv.pokemon.config.FrontendProperties;
import com.alexpauv.pokemon.exception.PasswordResetEmailSendingFailureException;
import com.alexpauv.pokemon.service.auth.SecurityProperties;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private static final String RESET_PASSWORD_EMAIL_SUBJECT = "Password Reset Request";

    @Value("${spring.mail.username}")
    private String senderUsername;

    private final FrontendProperties frontendProperties;

    private final SecurityProperties securityProperties;

    private final JavaMailSender mailSender;

    public EmailService(FrontendProperties frontendProperties, SecurityProperties securityProperties, JavaMailSender javaMailSender) {
        this.frontendProperties = frontendProperties;
        this.securityProperties = securityProperties;
        this.mailSender = javaMailSender;
    }

    private String generateResetPasswordEmailContent(String resetLink) {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head><style>" +
                "body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }" +
                ".container { max-width: 600px; margin: 0 auto; padding: 20px; }" +
                ".button { display: inline-block; padding: 12px 24px; background-color: #007bff; " +
                "color: white; text-decoration: none; border-radius: 4px; margin: 20px 0; }" +
                ".footer { margin-top: 30px; padding-top: 20px; border-top: 1px solid #eee; " +
                "font-size: 12px; color: #666; }" +
                "</style></head>" +
                "<body>" +
                "<div class='container'>" +
                "<h2>Password Reset Request</h2>" +
                "<p>Dear User,</p>" +
                "<p>You have requested to reset your password. Click the button below to proceed:</p>" +
                "<a href='" + resetLink + "' class='button'>Reset Password</a>" +
                "<p>Or copy and paste this link into your browser:</p>" +
                "<p><a href='" + resetLink + "'>" + resetLink + "</a></p>" +
                "<p><strong>This link will expire in " + securityProperties.passwordResetTokenValidityHours() + "hour(s).</strong></p>" +
                "<p>If you did not request this password reset, please ignore this email.</p>" +
                "<div class='footer'>" +
                "<p>Best regards,<br>The Team</p>" +
                "</div>" +
                "</div>" +
                "</body>" +
                "</html>";
    }

    public void sendPasswordResetEmail(String recipientEmail, String passwordResetToken) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "UTF-8");

            messageHelper.setFrom(senderUsername);
            messageHelper.setTo(recipientEmail);
            messageHelper.setSubject(RESET_PASSWORD_EMAIL_SUBJECT);

            String resetLink = frontendProperties.url() + "/reset-password?token=" + passwordResetToken;
            String emailContent = generateResetPasswordEmailContent(resetLink);

            messageHelper.setText(emailContent, true);

            mailSender.send(message);
        } catch (MessagingException e) {
            throw new PasswordResetEmailSendingFailureException("Failed to send reset password email: " + e.getMessage());
        }
    }
}
