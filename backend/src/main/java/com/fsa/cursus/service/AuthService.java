package com.fsa.cursus.service;

import com.fsa.cursus.model.request.AuthRequest;
import com.fsa.cursus.model.request.LoginRequest;
import com.fsa.cursus.model.request.RegisterRequest;
import com.fsa.cursus.model.response.AuthResponse;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);

    AuthResponse refreshToken(AuthRequest request);
}
