package org.example.frontend.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String fullName;
    private String username;
    private String role;
    private String accessToken;
    private String refreshToken;
}
