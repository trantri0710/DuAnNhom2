package org.example.frontend.config;

import org.example.frontend.security.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
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
        // Apply JwtAuthenticationFilter for all URLs except for login, error, and static resources
        registry.addInterceptor(securityInterceptor).addPathPatterns("/**").excludePathPatterns("/login", "/error/**", "/webjars/**", "/css/**", "/js/**", "/img/**", "/fonts/**, /favicon.ico");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/favicon.ico").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/**").addResourceLocations("classpath:/static/", "classpath:/resources/", "classpath:/static/img/, classpath:/static/css/, classpath:/static/js/");
    }
}
