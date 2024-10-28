package org.example.frontend.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.frontend.model.request.LoginRequest;
import org.example.frontend.model.request.AccountRequest;
import org.example.frontend.model.response.AccountResponse;
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
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private RestTemplate restTemplate;

    private final String API_URL_ACCOUNT = ConstantUtil.HOST_URL + "/api/accounts";

    @Override
    public ApiResponse login(LoginRequest loginRequest) {
        String apiUrl = ConstantUtil.HOST_URL + "/api/auth/login";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequest> httpEntity = new HttpEntity<>(loginRequest, headers);

        try {
            ResponseEntity<ApiResponse<AuthResponse>> responseEntity = restTemplate.exchange(apiUrl, HttpMethod.POST, httpEntity, new ParameterizedTypeReference<>() {
            });

            return responseEntity.getBody();
        } catch (HttpClientErrorException ex) {
            return handleClientException(ex);
        }
    }

    @Override
    public ApiResponse<List<AccountResponse>> getAllAccounts(int currentPage, int size, String accessToken) {
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL_ACCOUNT).queryParam("currentPage", currentPage).queryParam("size", size);

        HttpHeaders headers = createAuthHeaders(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ApiResponse<List<AccountResponse>>> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
            });

            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ApiResponse updateAccount(AccountRequest accountRequest, String accessToken) {
        HttpHeaders headers = createAuthHeaders(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AccountRequest> entity = new HttpEntity<>(accountRequest, headers);

        try {
            ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(API_URL_ACCOUNT + "/id/" + accountRequest.getAccountId(), HttpMethod.PUT, entity, ApiResponse.class);

            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ApiResponse addAccount(AccountRequest accountRequest, String accessToken) {
        HttpHeaders headers = createAuthHeaders(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<AccountRequest> entity = new HttpEntity<>(accountRequest, headers);

        try {
            ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(API_URL_ACCOUNT, HttpMethod.POST, entity, ApiResponse.class);

            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ApiResponse requestResetPassword(String email) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL_ACCOUNT + "/request-reset-password").queryParam("email", email);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, ApiResponse.class);
            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ApiResponse resetPassword(String token, String newPassword) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        params.put("newPassword", newPassword);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(API_URL_ACCOUNT + "/reset-password").queryParam("token", token).queryParam("newPassword", newPassword);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.POST, entity, ApiResponse.class);
            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ApiResponse countAllAccounts(String accessToken) {
        HttpHeaders headers = createAuthHeaders(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(API_URL_ACCOUNT + "/count", HttpMethod.GET, entity, ApiResponse.class);

            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ApiResponse getAccountById(Long accountId, String accessToken) {
        HttpHeaders headers = createAuthHeaders(accessToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ApiResponse<AccountResponse>> responseEntity = restTemplate.exchange(API_URL_ACCOUNT + "/id/" + accountId, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
            });

            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private HttpHeaders createAuthHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);
        return headers;
    }

    private ApiResponse handleClientException(HttpClientErrorException ex) {
        try {
            if (ex.getStatusCode() != HttpStatus.OK) {
                ObjectMapper objectMapper = new ObjectMapper();
                return objectMapper.readValue(ex.getResponseBodyAsString(), ApiResponse.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
