package com.fsa.cursus.service.impl;

import com.fsa.cursus.handle.MailHandler;
import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.repository.AccountRepository;
import com.fsa.cursus.service.AccountService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final MailHandler mailHandler;

    public AccountServiceImpl(AccountRepository accountRepository, MailHandler mailHandler) {
        this.accountRepository = accountRepository;
        this.mailHandler = mailHandler;
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

    @Override
    public void resetPassword(String email) {
        Account account = accountRepository.findByEmail(email).orElse(null);
        if (account != null) {
            String token = UUID.randomUUID().toString();
            account.setResetToken(token);
            accountRepository.save(account);

            String resetLink = "http://localhost:8888/account/reset-password?token=" + token;
            String emailBody = "<p>You requested to reset your password.</p>" + "<p>Click the link below to reset your password:</p>" + "<a href=\"" + resetLink + "\">Reset Password</a>";

            mailHandler.sendEmail(email, "Password Reset Request", emailBody);
        }
    }

    @Override
    public Account findByResetToken(String resetToken) {
        return accountRepository.findByResetToken(resetToken).orElse(null);
    }
}
