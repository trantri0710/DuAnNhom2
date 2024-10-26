package org.example.frontend.service;

import org.example.frontend.model.request.LoginRequest;
import org.example.frontend.model.request.RegisterRequest;
import org.example.frontend.model.response.ApiResponse;

public interface AccountService {

    ApiResponse login(LoginRequest loginRequest);
    ApiResponse register(RegisterRequest registerRequest);

}
