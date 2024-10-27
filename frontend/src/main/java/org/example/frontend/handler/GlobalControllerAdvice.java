package org.example.frontend.handler;

import jakarta.servlet.http.HttpSession;
import org.example.frontend.model.response.AuthResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalControllerAdvice {

    @ModelAttribute
    public void addAttributes(HttpSession session, Model model) {
        AuthResponse authResponse = (AuthResponse) session.getAttribute("userLogin");
        if (authResponse != null) {
            model.addAttribute("userName", authResponse.getUsername());
            model.addAttribute("userRole", authResponse.getRole());
        }
    }

}