package com.fsa.cursus.controller;

import com.fsa.cursus.model.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/private")
public class PrivateController {

    @GetMapping
    public ResponseEntity<ApiResponse> showData(Authentication authentication) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("dataCode", "200");
        metadata.put("dataMessage", authentication.getName());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(metadata);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "admin")
    public ResponseEntity<ApiResponse> getAdminRole() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("dataCode", "200");
        metadata.put("dataMessage", new Date().toString());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(metadata);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping(value = "all")
    public ResponseEntity<ApiResponse> getUserRole() {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("dataCode", "200");
        metadata.put("dataMessage", new Date().toString());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(metadata);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

    @PreAuthorize("#user == authentication.principal.username OR hasRole('ADMIN')")
    @GetMapping(value = "profile")
    public ResponseEntity<ApiResponse> getProfile(String user) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("dataCode", "200");
        metadata.put("dataMessage", new Date().toString());

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.ok(metadata);

        return new ResponseEntity<>(apiResponse, HttpStatus.OK);
    }

}
