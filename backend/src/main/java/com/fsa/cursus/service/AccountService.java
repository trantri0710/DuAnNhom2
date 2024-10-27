package com.fsa.cursus.service;


import com.fsa.cursus.model.entity.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AccountService {

    Account findByUsername(String username);
    Account saveOrUpdate(Account account);
    Page<Account> getAllAccount(Pageable pageable);
    Account getAccountById(Long accountId);
    Long countAllAccount();

}

