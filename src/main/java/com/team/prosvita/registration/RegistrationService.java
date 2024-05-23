package com.team.prosvita.registration;

import com.team.prosvita.email.IEmailSender;
import com.team.prosvita.entities.Role;
import com.team.prosvita.entities.User;
import com.team.prosvita.registration.token.ConfirmationToken;
import com.team.prosvita.registration.token.ConfirmationTokenService;
import com.team.prosvita.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationService {

//    private final UserService userService;
//    private final EmailValidator emailValidator;
//    private final UsernameValidator usernameValidator;
//    private final ConfirmationTokenService confirmationTokenService;
//    private final IEmailSender emailSender;
//
//    public String register(RegistrationRequest request) {
//        boolean isValidEmail = emailValidator.test(request.getEmail());
//        boolean isValidUsername = usernameValidator.test(request.getUsername());
//
//        if (!isValidEmail) {
//            log.error("Invalid email: {}", request.getEmail());
//            throw new IllegalStateException("Email not valid!");
//        }
//
//        if (!isValidUsername) {
//            log.error("Invalid username: {}", request.getUsername());
//            throw new IllegalStateException("Username not valid!");
//        }
//
//        //try to correct order of variables: username, name, surname
//        User newUser = new User(
//                request.getName(),
//                request.getSurname(),
//                request.getUsername(),
//                request.getEmail(),
//                request.getPassword(),
//                Role.USER
//        );
//
////        String token = userService.signUpUser(new User(request.getName(), request.getSurname(),
////                request.getUsername(), request.getEmail(), request.getPassword(), Role.USER));
//
//        String token = userService.signUpUser(newUser);
//        String link = "http://localhost:8080/registration/register/confirm?token=" + token;
//
//
//        emailSender.send(request.getEmail(), buildEmail(request.getName(), link));
//
//        log.info("Registering user: {}", request.getEmail());
//        log.info("Confirmation token: {}", token);
//        log.info("Confirmation link: {}", link);
////        return userService.signUpUser(newUser);
//        return token;
//    }
//
//    @Transactional
//    public String confirmToken (String token){
//        log.info("Confirming token: {}", token);
//        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
//                .orElseThrow(() -> new IllegalStateException("Token not found!"));
//
//        if (confirmationToken.getConfirmedAt() != null) {
//            log.info("Email already confirmed for token: {}", token);
//            throw new IllegalStateException("Email already confirmed!");
//        }
//
//        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
//
//        if (expiredAt.isBefore(LocalDateTime.now())){
//            log.error("Token expired: {}", token);
//            throw new IllegalStateException("Token expired!");
//        }
//
//        confirmationTokenService.setConfirmedAt(token);
//        userService.enableUser(confirmationToken.getUser().getEmail());
//
//        log.info("User {} enabled successfully", confirmationToken.getUser().getEmail());
//
//        return "confirmed";
//    }

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final UsernameValidator usernameValidator;
    private final ConfirmationTokenService confirmationTokenService;
    private final IEmailSender emailSender;

    public String register(RegistrationRequest request) {
        boolean isValidEmail = emailValidator.test(request.getEmail());
        boolean isValidUsername = usernameValidator.test(request.getUsername());

        if (!isValidEmail) {
            log.error("Invalid email: {}", request.getEmail());
            throw new IllegalStateException("Email not valid!");
        }

        if (!isValidUsername) {
            log.error("Invalid username: {}", request.getUsername());
            throw new IllegalStateException("Username not valid!");
        }

        User newUser = new User(
                request.getName(),
                request.getSurname(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                Role.USER
        );

        String token = userService.signUpUser(newUser);
        String link = "http://localhost:8080/registration/register/confirm?token=" + token;

        emailSender.send(request.getEmail(), buildEmail(request.getName(), link));

        log.info("Registering user: {}", request.getEmail());
        log.info("Generated confirmation token: {}", token);
        log.info("Confirmation link: {}", link);

        return token;
    }

    @Transactional
    public String confirmToken(String token) {
        log.info("Confirming token: {}", token); // Added log statement
        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                .orElseThrow(() -> new IllegalStateException("Token not found!"));

        if (confirmationToken.getConfirmedAt() != null) {
            log.info("Email already confirmed for token: {}", token);
            throw new IllegalStateException("Email already confirmed!");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();
        if (expiredAt.isBefore(LocalDateTime.now())) {
            log.error("Token expired: {}", token);
            throw new IllegalStateException("Token expired!");
        }

        confirmationTokenService.setConfirmedAt(token);
        String email = confirmationToken.getUser().getEmail();
        userService.enableUser(email);

        log.info("User {} enabled successfully", email);

        return "confirmed";
    }

    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }

}