package com.team.prosvita.registration.password;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/reset-password")
@AllArgsConstructor
@Slf4j
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    @GetMapping("/request")
    public String showPasswordResetRequestForm(Model model) {
        model.addAttribute("passwordResetRequest", new PasswordResetRequest());
        return "password-reset-request";
    }

    @PostMapping("/request")
    public String handlePasswordResetRequest(@ModelAttribute @Valid PasswordResetRequest request, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("Binding result has errors: {}", bindingResult.getAllErrors());
            return "password-reset-request";
        }

        try {
            passwordResetService.resetPassword(request);
            log.info("Reset password request processed for email: {}", request.getEmail());
            return "password-reset-success";
        } catch (IllegalStateException e) {
            log.error("Reset password request error: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            return "password-reset-request";
        }
    }

    @GetMapping("/reset")
    public String showPasswordResetForm(@RequestParam("token") String token, Model model) {
        model.addAttribute("token", token);
        model.addAttribute("passwordResetForm", new NewPasswordRequest());
        return "new-password-form";
    }

    @PostMapping("/reset")
    public String handlePasswordReset(@RequestParam("token") String token,
                                      @ModelAttribute @Valid NewPasswordRequest form,
                                      BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            log.error("Binding result has errors: {}", bindingResult.getAllErrors());
            model.addAttribute("token", token);
            return "new-password-form";
        }

        if (!form.getNewPassword().equals(form.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "Passwords do not match.");
            log.error("Passwords do not match");
            model.addAttribute("token", token);
            return "new-password-form";
        }

        try {
            passwordResetService.confirmPasswordResetToken(token, form.getNewPassword(), form.getConfirmPassword());
            log.info("Password reset successfully for token: {}", token);
            return "redirect:/auth/login";
        } catch (IllegalStateException e) {
            log.error("Password reset error: {}", e.getMessage());
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("token", token);
            return "new-password-form";
        }
    }
}