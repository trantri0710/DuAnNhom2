package com.fsa.cursus.controller;


import com.fsa.cursus.model.request.AuthRequest;
import com.fsa.cursus.model.request.LoginRequest;
import com.fsa.cursus.model.request.RegisterRequest;
import com.fsa.cursus.model.response.ApiResponse;
import com.fsa.cursus.model.response.AuthResponse;
import com.fsa.cursus.service.AuthService;
import com.fsa.cursus.util.ValidatorUtil;
import com.fsa.cursus.validator.RegisterValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RegisterValidator registerValidator;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterRequest request, BindingResult bindingResult) {
        registerValidator.validate(request, bindingResult);

        ApiResponse apiResponse = new ApiResponse();

        if (bindingResult.hasErrors()) {
            apiResponse.error("Bad request", ValidatorUtil.toErrors(bindingResult.getFieldErrors()));
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        AuthResponse authResponse = authService.register(request);
        apiResponse.ok(authResponse);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody LoginRequest request, BindingResult bindingResult) {
        ApiResponse apiResponse = new ApiResponse();

        if (bindingResult.hasErrors()) {
            apiResponse.error("Bad request");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        AuthResponse authResponse = authService.login(request);
        if (authResponse == null) {
            apiResponse.error("Username or password is incorrect.");
            return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
        }

        apiResponse.ok(authResponse);
        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshToken(@RequestBody AuthRequest request) {
        AuthResponse authResponse = authService.refreshToken(request);

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(authResponse);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}

