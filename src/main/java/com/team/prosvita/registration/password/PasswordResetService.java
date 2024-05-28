package com.team.prosvita.registration.password;

import com.team.prosvita.email.IEmailSender;
import com.team.prosvita.registration.EmailValidator;
import com.team.prosvita.registration.token.PasswordResetToken;
import com.team.prosvita.registration.token.PasswordResetTokenService;
import com.team.prosvita.service.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class PasswordResetService {

    private final EmailValidator emailValidator;
    private final UserService userService;
    private final PasswordResetTokenService passwordResetTokenService;
    private final IEmailSender emailSender;

    public void resetPassword(PasswordResetRequest passwordResetRequest) {
        boolean isValidEmail = emailValidator.test(passwordResetRequest.getEmail());
        if (!isValidEmail) {
            log.error("Invalid email: {}", passwordResetRequest.getEmail());
            throw new IllegalStateException("Email not valid!");
        }

        String token = userService.resetPassword(passwordResetRequest.getEmail());
        String link = "http://localhost:8080/reset-password/reset?token=" + token;

        // TODO: check if correct pass reset
        //emailSender.send(passwordResetRequest.getEmail(), buildEmail(passwordResetRequest.getEmail(), link));
        emailSender.sendPasswordReset(passwordResetRequest.getEmail(), buildEmail(passwordResetRequest.getEmail(), link));

        log.info("Reset password for email: {}", passwordResetRequest.getEmail());
        log.info("Generated reset token: {}", token);
        log.info("Reset link: {}", link);
    }

    @Transactional
    public String confirmPasswordResetToken(String token, String newPassword, String confirmPassword) {
        log.info("Confirming password reset token: {}", token);

        PasswordResetToken passwordResetToken = passwordResetTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found!"));

        if (passwordResetToken.getResetAt() != null) {
            log.info("Password reset token already confirmed: {}", token);
            throw new IllegalStateException("Password reset token already used!");
        }

        LocalDateTime expiredAt = passwordResetToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            log.error("Password reset token expired: {}", token);
            throw new IllegalStateException("Password reset token expired!");
        }

        if (!newPassword.equals(confirmPassword)) {
            throw new IllegalStateException("Passwords do not match!");
        }

        userService.updatePassword(token, newPassword);
        passwordResetTokenService.setResetAt(token);
        log.info("Password reset token {} used successfully", token);

        return "Token used successfully. You can now use your new password.";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\"></td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Reset your password</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p>\n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> We received a request to reset your password. Please click on the link below to reset your password: </p>\n" +
                "            <blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Reset Password</a> </p></blockquote>\n" +
                "            <p>Link will expire in 15 minutes.</p>\n" +
                "            <p>If you did not request a password reset, please ignore this email.</p>\n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\"></div></div>";
    }
}