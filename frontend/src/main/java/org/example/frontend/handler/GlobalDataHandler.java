package org.example.frontend.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.example.frontend.model.response.AuthResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.text.DecimalFormat;

@ControllerAdvice(annotations = Controller.class)
public class GlobalDataHandler {

    private static final DecimalFormat df = new DecimalFormat("###,###,###");

    @ModelAttribute("userLogin")
    public AuthResponse getCurrentUser(HttpSession session) {
        if (session == null) {
            return null;

        }

        AuthResponse authResponse = (AuthResponse) session.getAttribute("userLogin");
        return authResponse;
    }

    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }

}


