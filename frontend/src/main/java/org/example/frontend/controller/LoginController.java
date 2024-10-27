package org.example.frontend.controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.example.frontend.model.request.LoginRequest;
import org.example.frontend.model.response.ApiResponse;
import org.example.frontend.model.response.AuthResponse;
import org.example.frontend.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/login")
public class LoginController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public String viewPage(Model model) {
        LoginRequest loginRequest = new LoginRequest();
        model.addAttribute("user", loginRequest);
        model.addAttribute("error", null);
        return "login";
    }

    @PostMapping
    public String submitLogin(Model model, @Valid LoginRequest loginRequest, BindingResult bindingResult, HttpSession session) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("user", loginRequest);
            model.addAttribute("error", "Please enter username and password.");
            return "login";
        }

        ApiResponse<AuthResponse> apiResponse = accountService.login(loginRequest);
        if (apiResponse == null || apiResponse.getPayload() == null) {
            model.addAttribute("user", loginRequest);
            model.addAttribute("error", "The username or password is incorrect.");
            return "login";
        }

        // Get user data (this might include roles, or additional user information)
        AuthResponse authResponse = apiResponse.getPayload();

        // Store JWT in session
        session.setAttribute("userLogin", authResponse);

        return "redirect:/home"; // Redirect to home after successful login
    }
}
