/*package com.team.prosvita.registration.password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PasswordResetRequest {
    @Email
    @NotEmpty
    private String email;

    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String newPassword;

    @Size(min = 8, message = "Confirm password must be at least 8 characters long")
    private String confirmPassword;
}*/

package com.team.prosvita.registration.password;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class PasswordResetRequest {
    @Email
    @NotEmpty
    private String email;
}
