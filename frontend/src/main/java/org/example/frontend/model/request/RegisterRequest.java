package org.example.frontend.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest implements Serializable {

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required.")
    private String password;

    @NotBlank(message = "Fullname is required.")
    private String fullname;

    @NotBlank(message = "Status is required.")
    private boolean status;

    @NotBlank(message = "Role is required.")
    private String role;

}
