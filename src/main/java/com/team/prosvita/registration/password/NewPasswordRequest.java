package com.team.prosvita.registration.password;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class NewPasswordRequest {
    @Size(min = 8, message = "Password must be at least 6 characters long")
    private String newPassword;

    @Size(min = 8, message = "Confirm password must be at least 6 characters long")
    private String confirmPassword;
}
