package com.fsa.cursus.service;


import com.fsa.cursus.model.entity.Account;

public interface AccountService {

    Account findByUsername(String username);

    Account saveOrUpdate(Account account);

}

