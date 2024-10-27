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
public class AccountRequest implements Serializable {

    private Long accountId;

    @NotBlank(message = "Username is required.")
    private String username;

    @NotBlank(message = "Password is required.")
    private String password;

    @NotBlank(message = "Full name is required.")
    private String fullName;

    @NotBlank(message = "Status is required.")
    private boolean status;

    @NotBlank(message = "Role is required.")
    private String role;

}
