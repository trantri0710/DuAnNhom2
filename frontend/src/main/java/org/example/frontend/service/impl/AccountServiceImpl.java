package org.example.frontend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.frontend.model.request.LoginRequest;
import org.example.frontend.model.request.RegisterRequest;
import org.example.frontend.model.response.ApiResponse;
import org.example.frontend.model.response.AuthResponse;
import org.example.frontend.service.AccountService;
import org.example.frontend.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private RestTemplate restTemplate;

    private String apiUrl = ConstantUtil.HOST_URL + "/api/auth/login";
    private String apiUrlRegister = ConstantUtil.HOST_URL + "/api/auth/register";

    @Override
    public ApiResponse login(LoginRequest loginRequest) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity<>(loginRequest, headers);

            ResponseEntity<ApiResponse<AuthResponse>> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
            });

            ApiResponse response = responseEntity.getBody();
            return response;
        } catch (HttpClientErrorException ex) {
            try {
                if (ex.getStatusCode() != HttpStatus.OK) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ApiResponse apiResponse = objectMapper.readValue(ex.getResponseBodyAsString(), ApiResponse.class);
                    return apiResponse;
                }
            } catch (Exception e) {
                return null;
            }
        }

        return null;
    }

    @Override
    public ApiResponse register(RegisterRequest registerRequest) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity httpEntity = new HttpEntity<>(registerRequest, headers);

            ResponseEntity<ApiResponse<AuthResponse>> responseEntity = restTemplate.exchange(apiUrlRegister, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
            });

            ApiResponse response = responseEntity.getBody();
            return response;
        } catch (HttpClientErrorException ex) {
            try {
                if (ex.getStatusCode() != HttpStatus.OK) {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ApiResponse apiResponse = objectMapper.readValue(ex.getResponseBodyAsString(), ApiResponse.class);
                    return apiResponse;
                }
            } catch (Exception e) {
                return null;
            }
        }
        return null;
    }
}
