package com.fsa.cursus.service;


import com.fsa.cursus.model.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    Account findByUsername(String username);

    Account saveOrUpdate(Account account);

    // Hiển thị danh sách tất cả tài khoản
    Page<Account> getAllAccount(Pageable pageable);

    // Hiển thị chi tiết tài khoản theo ID
    Account getAccountById(Long accountId);

    //count all account
    Long countAllAccount();
}

