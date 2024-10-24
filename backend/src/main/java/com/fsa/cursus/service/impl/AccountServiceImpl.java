package com.fsa.cursus.service.Impl;

import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.repository.AccountRepository;
import com.fsa.cursus.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account findByUsername(String username) {
        return accountRepository.findByUsername(username).orElse(null);
    }

    @Override
    public Account saveOrUpdate(Account account) {
        return accountRepository.save(account);
    }

}
