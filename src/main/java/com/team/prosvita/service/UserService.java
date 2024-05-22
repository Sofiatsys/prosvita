package com.team.prosvita.service;

import com.team.prosvita.entities.User;
import com.team.prosvita.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {
    private final IUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private static final String USER_NOT_FOUND_MSG = "User %s not found!";

    //    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        return userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
//    }

    //load user by email or username simultaneously
    @Override
    public UserDetails loadUserByUsername(String identifier) throws UsernameNotFoundException {
        return userRepository.findByEmail(identifier)
                .or(() -> userRepository.findByUsername(identifier))
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, identifier)));
    }

    public String signUpUser(User user) {
        boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
        boolean usernameExists = userRepository.findByUsername(user.getUsername()).isPresent();

        if (userExists) {
            throw new IllegalStateException("Email already taken!");
        }

        if (usernameExists) {
            throw new IllegalStateException("Username already taken!");
        }

        String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userRepository.save(user);

        // TODO: Send confirmation token (Anna)

        return "User registration successful";
    }
    public boolean isUserCredentialsValid(String email, String password) {
        // add check for existence with username as well
        return userRepository.existsUserByEmailAndPassword(email, password);
    }
}
