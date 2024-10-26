package org.example.frontend.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/logout")
public class LogoutController {

    @GetMapping
    public String submitLogout(Model model, HttpSession session) {
        // Xóa session
        session.invalidate();

        return "redirect:/login";
    }

}
