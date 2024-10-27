// AccountMapper.java
package com.fsa.cursus.model.mapper;

import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.model.response.AccountResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AccountMapper {

    public AccountResponse convertToDTO(Account account) {
        if (account == null) {
            return null;
        }
        AccountResponse accountResponse = new AccountResponse();
        accountResponse.setAccountId(account.getAccountId());
        accountResponse.setFullName(account.getFullName());
        accountResponse.setPassword(account.getPassword());
        accountResponse.setRole(account.getRole());
        accountResponse.setStatus(account.isStatus());
        accountResponse.setUsername(account.getUsername());

        return accountResponse;
    }

    public List<AccountResponse> convertToDTO(List<Account> accountList) {
        if (accountList == null) {
            return null;
        }

        List<AccountResponse> accountResponseList = new ArrayList<>();
        for (Account account : accountList) {
            AccountResponse accountResponse = convertToDTO(account);
            accountResponseList.add(accountResponse);
        }
        return accountResponseList;
    }
}