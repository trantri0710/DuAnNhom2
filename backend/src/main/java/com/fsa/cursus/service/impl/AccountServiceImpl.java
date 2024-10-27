package com.fsa.cursus.service.impl;

import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.repository.AccountRepository;
import com.fsa.cursus.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Account saveOrUpdate(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public Page<Account> getAllAccount(Pageable pageable) {
        return accountRepository.findAll(pageable);
    }

    @Override
    public Account getAccountById(Long accountId) {
        return accountRepository.findById(accountId).orElse(null);
    }

    @Override
    public Long countAllAccount() {
        return accountRepository.count();
    }
}
