package org.example.frontend.service;

import org.example.frontend.model.request.LoginRequest;
import org.example.frontend.model.request.AccountRequest;
import org.example.frontend.model.response.ApiResponse;

public interface AccountService {

    ApiResponse login(LoginRequest loginRequest);
    ApiResponse getAllAccounts(int currentPage, int size, String accessToken);
    ApiResponse countAllAccounts(String accessToken);
    ApiResponse getAccountById(Long accountId, String accessToken);
    ApiResponse updateAccount(AccountRequest accountRequest, String accessToken);
    ApiResponse addAccount(AccountRequest accountRequest, String accessToken);
}
