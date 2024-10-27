package com.fsa.cursus.controller;

import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.model.mapper.AccountMapper;
import com.fsa.cursus.model.response.ApiResponse;
import com.fsa.cursus.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // API hiển thị danh sách tất cả account
    @GetMapping
    public ResponseEntity<ApiResponse> getAllAccount(@RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "10") Integer size) {

        Pageable pageable = PageRequest.of(currentPage - 1, size);
        Page<Account> accountPage = accountService.getAllAccount(pageable);

        ApiResponse response = new ApiResponse();

        response.ok("OK", accountMapper.convertToDTO(accountPage.getContent()));
        response.setPaginationMetadata(accountPage.getTotalElements(), accountPage.getTotalPages(), accountPage.getNumber(), accountPage.getSize());

        return ResponseEntity.ok(response);  // Trả về danh sách khóa học dưới dạng JSON
    }

    // API hiển thị chi tiết account theo ID
    @GetMapping("/id/{accountId}")
    public ResponseEntity<ApiResponse> getByAccount(@PathVariable Long accountId) {
        Account account = accountService.getAccountById(accountId);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        ApiResponse response = new ApiResponse();
        response.ok("OK", accountMapper.convertToDTO(account));

        return ResponseEntity.ok(response);
    }

    // After
    @PutMapping("/id/{accountId}")
    public ResponseEntity<ApiResponse> updateAccount(@PathVariable Long accountId, @RequestBody Account account) {
        try {
            Account existingAccount = accountService.getAccountById(accountId);
            if (existingAccount == null) {
                return ResponseEntity.notFound().build();
            }
            existingAccount.setUsername(account.getUsername());
            existingAccount.setFullName(account.getFullName());
            existingAccount.setRole(account.getRole());
            existingAccount.setStatus(account.isStatus());
            Account updatedAccount = accountService.saveOrUpdate(existingAccount);
            ApiResponse response = new ApiResponse();
            response.ok("OK", updatedAccount);

            return ResponseEntity.ok(response);
        } catch (HttpClientErrorException.Forbidden ex) {
            ApiResponse response = new ApiResponse();
            response.error("You do not have permission to access this resource.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(response);
        } catch (Exception ex) {
            ApiResponse response = new ApiResponse();
            response.error("An error occurred while updating the account.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    // API create account
    @PostMapping
    public ResponseEntity<ApiResponse> createAccount(@RequestBody Account account) {
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Account account1 = accountService.saveOrUpdate(account);
        ApiResponse response = new ApiResponse();
        response.ok("OK", accountMapper.convertToDTO(account1));
        return ResponseEntity.ok(response);
    }

    // Api count all account
    @GetMapping("/count")
    public ResponseEntity<ApiResponse> countAllAccount() {
        Long count = accountService.countAllAccount();
        ApiResponse response = new ApiResponse();
        response.ok("OK", count);
        return ResponseEntity.ok(response);
    }
}
