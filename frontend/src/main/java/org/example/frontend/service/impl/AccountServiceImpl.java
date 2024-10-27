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

    private String apiUrl = ConstantUtil.HOST_URL + "/api/auth/login";
    private String apiUrlAccount = ConstantUtil.HOST_URL + "/api/accounts";

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
    public ApiResponse<List<AccountResponse>> getAllAccounts(int currentPage, int size, String accessToken) {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("currentPage", currentPage);
            params.put("size", size);

            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrlAccount);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.queryParam(entry.getKey(), entry.getValue());
            }

            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

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
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<AccountRequest> entity = new HttpEntity<>(accountRequest, headers);

            ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(apiUrlAccount + "/id/" + accountRequest.getAccountId(), HttpMethod.PUT, entity, ApiResponse.class);

            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ApiResponse addAccount(AccountRequest accountRequest, String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<AccountRequest> entity = new HttpEntity<>(accountRequest, headers);

            ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(apiUrlAccount, HttpMethod.POST, entity, ApiResponse.class);

            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ApiResponse countAllAccounts(String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<ApiResponse> responseEntity = restTemplate.exchange(apiUrlAccount + "/count", HttpMethod.GET, entity, ApiResponse.class);

            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ApiResponse getAccountById(Long accountId, String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<ApiResponse<AccountResponse>> responseEntity = restTemplate.exchange(apiUrlAccount + "/id/" + accountId, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
            });
            return responseEntity.getBody();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
