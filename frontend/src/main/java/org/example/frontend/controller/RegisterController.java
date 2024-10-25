package org.example.frontend.controller;

import org.example.frontend.model.request.RegisterRequest;
import org.example.frontend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/register")
public class RegisterController {

    @Autowired
    private AccountService accountService;

    @RequestMapping
    public String viewPage() {
        return "register";
    }

    @RequestMapping(value = "/submit")
    public String submitRegister(RegisterRequest registerRequest) {
        accountService.register(registerRequest);
        return "redirect:/login";
    }
}
