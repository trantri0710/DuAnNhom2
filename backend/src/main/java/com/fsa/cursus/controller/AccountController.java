package com.fsa.cursus.controller;

import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.model.mapper.AccountMapper;
import com.fsa.cursus.model.response.ApiResponse;
import com.fsa.cursus.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final PasswordEncoder passwordEncoder;

    public AccountController(AccountService accountService, AccountMapper accountMapper, PasswordEncoder passwordEncoder) {
        this.accountService = accountService;
        this.accountMapper = accountMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllAccount(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(currentPage - 1, size);
        Page<Account> accountPage = accountService.getAllAccount(pageable);

        ApiResponse response = createApiResponse("OK", accountMapper.convertToDTO(accountPage.getContent()));
        response.setPaginationMetadata(accountPage.getTotalElements(), accountPage.getTotalPages(), accountPage.getNumber(), accountPage.getSize());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/id/{accountId}")
    public ResponseEntity<ApiResponse> getByAccount(@PathVariable Long accountId) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createApiResponse("Account not found", null));
        }

        ApiResponse response = createApiResponse("OK", accountMapper.convertToDTO(account));
        return ResponseEntity.ok(response);
    }

    @PutMapping("/id/{accountId}")
    public ResponseEntity<ApiResponse> updateAccount(@PathVariable Long accountId, @RequestBody Account account) {
        Account existingAccount = accountService.getAccountById(accountId);
        if (existingAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(createApiResponse("Account not found", null));
        }

        existingAccount.setUsername(account.getUsername());
        existingAccount.setFullName(account.getFullName());
        existingAccount.setRole(account.getRole());
        existingAccount.setStatus(account.isStatus());

        Account updatedAccount = accountService.saveOrUpdate(existingAccount);
        ApiResponse response = createApiResponse("Account updated successfully", accountMapper.convertToDTO(updatedAccount));

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createAccount(@RequestBody Account account) {
        if (accountService.findByUsername(account.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(createApiResponse("Username already exists", null));
        }
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Account savedAccount = accountService.saveOrUpdate(account);

        ApiResponse response = createApiResponse("Account created successfully", accountMapper.convertToDTO(savedAccount));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponse> countAllAccount() {
        Long count = accountService.countAllAccount();
        ApiResponse response = createApiResponse("Total accounts count", count);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/request-reset-password")
    public ResponseEntity<ApiResponse> requestResetPassword(@RequestParam String email) {
        accountService.resetPassword(email);
        return ResponseEntity.ok(createApiResponse("Password reset link sent successfully", null));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse> resetPassword(@RequestParam String token, @RequestParam String newPassword) {
        Account account = accountService.findByResetToken(token);
        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(createApiResponse("Invalid token", null));
        }
        account.setPassword(passwordEncoder.encode(newPassword));  // Hash the new password
        account.setResetToken(null);                               // Clear the reset token after use
        accountService.saveOrUpdate(account);
        return ResponseEntity.ok(createApiResponse("Password reset successfully", null));
    }

    private ApiResponse createApiResponse(String message, Object data) {
        ApiResponse response = new ApiResponse();
        response.ok(message, data);
        return response;
    }
}
