package org.example.frontend.controller;

import jakarta.servlet.http.HttpSession;
import org.example.frontend.model.response.AuthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"", "/", "/home"})
public class HomeController {

    @GetMapping
    public String homePage(HttpSession session, Model model) {
        AuthResponse authResponse = (AuthResponse) session.getAttribute("userLogin");

        if (authResponse == null) {
            return "redirect:/login";
        }

        String role = authResponse.getRole();
        String username = authResponse.getUsername();
        model.addAttribute("userRole", role);
        model.addAttribute("userName", username);
        return "dashboard";
    }
}