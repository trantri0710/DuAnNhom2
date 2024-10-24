package com.fsa.cursus.service.Impl;


import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.model.request.AuthRequest;
import com.fsa.cursus.model.request.LoginRequest;
import com.fsa.cursus.model.request.RegisterRequest;
import com.fsa.cursus.model.response.AuthResponse;
import com.fsa.cursus.security.CustomUserDetails;
import com.fsa.cursus.security.JwtToken;
import com.fsa.cursus.service.AccountService;
import com.fsa.cursus.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private AccountService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public AuthResponse register(RegisterRequest request) {
        // new User
        Account user = new Account();
        user.setFullName(request.getFullName());
        user.setUsername(request.getUsername().trim().toLowerCase());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole().toUpperCase());
        user.setStatus(false);

        // Save
        userService.saveOrUpdate(user);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setFullName(user.getFullName());
        authResponse.setUsername(user.getUsername());
        authResponse.setRole(user.getRole());

        return authResponse;
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Account user = userService.findByUsername(request.getUsername());
        if (user != null) {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            CustomUserDetails userSecurity = new CustomUserDetails(user);

            Map<String, Object> extraClaims = new HashMap<>();
            extraClaims.put("fullName", user.getFullName());
            extraClaims.put("username", user.getUsername());
            extraClaims.put("role", user.getRole());
            extraClaims.put("authorities", userSecurity.getAuthorities());

            String accessToken = jwtToken.generateToken(extraClaims, userSecurity);
            String refreshToken = jwtToken.generateRefreshToken(userSecurity);

            AuthResponse authResponse = new AuthResponse();
            authResponse.setFullName(user.getFullName());
            authResponse.setUsername(user.getUsername());
            authResponse.setRole(user.getRole());
            authResponse.setAccessToken(accessToken);
            authResponse.setRefreshToken(refreshToken);

            return authResponse;
        }

        return null;
    }

    @Override
    public AuthResponse refreshToken(AuthRequest request) {
        return null;
    }

}

