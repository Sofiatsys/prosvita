package com.team.prosvita.registration;

import com.team.prosvita.entities.Role;
import com.team.prosvita.entities.User;
import com.team.prosvita.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class RegistrationService {

    private final UserService userService;
    private final EmailValidator emailValidator;
    private final UsernameValidator usernameValidator;

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

        //try to correct order of variables: username, name, surname
        User newUser = new User(
                request.getName(),
                request.getSurname(),
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                Role.USER
        );

        log.info("Registering user: {}", request.getEmail());
        return userService.signUpUser(newUser);
    }
}