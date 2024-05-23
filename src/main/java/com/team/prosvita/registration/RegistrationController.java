package com.team.prosvita.registration;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
@AllArgsConstructor
@Slf4j
public class RegistrationController {

//    private final RegistrationService registrationService;
//
//    @GetMapping("/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("registrationRequest", new RegistrationRequest());
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public String register(@ModelAttribute @Valid RegistrationRequest request, BindingResult bindingResult, Model model) {
//        if (bindingResult.hasErrors()) {
//            log.error("Binding result has errors: {}", bindingResult.getAllErrors());
//            return "register";
//        }
//        try {
//            log.info("name: {}, surname: {}, username: {}, email: {}",
//                    request.getName(), request.getSurname(), request.getUsername(), request.getEmail());
//            registrationService.register(request);
//            log.info("Redirecting user to the home page after successful registration");
//            return "redirect:/auth/login";
//        } catch (IllegalStateException e) {
//            log.error("Registration error: {}", e.getMessage());
//            model.addAttribute("errorMessage", e.getMessage());
//            return "register";
//        }
//    }
//    @GetMapping("/register/confirm")
//    public String confirm(@RequestParam("token") String token, Model model) {
//        log.info("Received token for confirmation: {}", token);
//        String result = registrationService.confirmToken(token);
//        log.info("Confirmation result: {}", result);
//        model.addAttribute("message", result);
//        return "confirmation";
//    }

    private final RegistrationService registrationService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute @Valid RegistrationRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("Binding result has errors: {}", bindingResult.getAllErrors());
            return "register";
        }
        try {
            log.info("name: {}, surname: {}, username: {}, email: {}",
                    request.getName(), request.getSurname(), request.getUsername(), request.getEmail());
            registrationService.register(request);
            log.info("Redirecting user to the home page after successful registration");
            return "redirect:/auth/login";
        } catch (IllegalStateException e) {
            log.error("Registration error: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "register";
        }
    }

    @GetMapping("/register/confirm")
    public String confirm(@RequestParam("token") String token, Model model) {
        log.info("Confirm method called with token: {}", token); // Added log statement
        String result = registrationService.confirmToken(token);
        model.addAttribute("message", result);
        return "confirmation";
    }

}