package com.fsa.cursus.validator;

import com.fsa.cursus.model.entity.Account;
import com.fsa.cursus.model.request.RegisterRequest;
import com.fsa.cursus.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class RegisterValidator implements Validator {

    @Autowired
    private AccountService accountService;

    @Override
    public boolean supports(Class<?> clazz) {
        return RegisterRequest.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RegisterRequest request = (RegisterRequest) target;

        Account account = accountService.findByUsername(request.getUsername().trim().toLowerCase());
        if  (account != null) {
            errors.rejectValue("username", "Username already exists.","Username already exists.");
        }
    }

}
