package com.fsa.cursus.security;

import com.fsa.cursus.exception.InvalidTokenException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtToken jwtToken;

    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            if (request.getServletPath().contains("/api/auth")) {
                filterChain.doFilter(request, response);
                return;
            }
            if (request.getServletPath().contains("/v3/api-docs") || request.getServletPath().contains("/swagger-ui")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Lấy JWT từ request
            final String authHeader = request.getHeader("Authorization");
            final String jwt;
            final String username;

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                filterChain.doFilter(request, response);
                return;
            }

            // Lấy username từ chuỗi JWT
            jwt = authHeader.substring(7);
            username = jwtToken.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                // Lấy thông tin người dùng
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                // Nếu người dùng hợp lệ, set thông tin cho Seturity Context
                if (jwtToken.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                } else {
                    throw new InvalidTokenException("Invalid Token");
                }
            }

            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            throw ex;
        }
    }

}
