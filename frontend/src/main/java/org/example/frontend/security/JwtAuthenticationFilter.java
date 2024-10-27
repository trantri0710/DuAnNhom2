package org.example.frontend.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.example.frontend.model.response.AuthResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtAuthenticationFilter implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false); // Do not create a new session if none exists

        // Redirect to login if session does not exist
        if (session == null) {
            response.sendRedirect("/login");
            return false;
        }

        // Check if the user is logged in
        AuthResponse userLogin = (AuthResponse) session.getAttribute("userLogin");
        if (userLogin == null) {
            response.sendRedirect("/login");
            return false;
        }

        // Get token and validate it
        String token = userLogin.getAccessToken();
        if (token == null || !jwtUtil.isTokenValid(token, userLogin.getUsername())) {
            response.sendRedirect("/login");
            return false;
        }

        // Check access permissions
        String requestURI = request.getRequestURI();
        String userRole = userLogin.getRole();

        // Allow access for ADMIN and INSTRUCTOR only
        if (userRole.equals("ADMIN") || userRole.equals("INSTRUCTOR")) {
            return true; // Allow access
        }

        // Redirect to unauthorized page for any other roles and remove session
        session.removeAttribute("userLogin");
        response.sendRedirect("/error/unauthorized");
        return false; // Deny access
    }
}
