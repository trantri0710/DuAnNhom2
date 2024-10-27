package org.example.frontend.config;

import org.example.frontend.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AppConfig implements WebMvcConfigurer {

    @Autowired
    private JwtAuthenticationFilter securityInterceptor;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Apply JwtAuthenticationFilter for all URLs except /login and /error/**
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/**") // Apply to all paths
                .excludePathPatterns("/login", "/error/**"); // Exclude specific paths
    }
}
