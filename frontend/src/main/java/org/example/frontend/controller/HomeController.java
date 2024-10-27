package org.example.frontend.controller;

import org.example.frontend.model.response.ApiResponse;
import org.example.frontend.model.response.AuthResponse;
import org.example.frontend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping(value = {"", "/", "/home"})
public class HomeController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String homePage(Model model, HttpSession session) {
        AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");
        if (userLogin != null) {
            ApiResponse accountCountResponse = accountService.countAllAccounts(userLogin.getAccessToken());
            Integer count = (Integer) accountCountResponse.getPayload();
            model.addAttribute("accountCount", count);
        } else {
            return "redirect:/login";
        }
        return "dashboard";
    }

}