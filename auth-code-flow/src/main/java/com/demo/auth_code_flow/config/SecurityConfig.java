package com.demo.auth_code_flow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration for OAuth2 Authorization Code Flow
 * This configures the application to use OAuth2 authorization code flow,
 * which allows users to authenticate through a browser-based login process.
 * 
 * Unlike client credentials flow (machine-to-machine), the authorization code
 * flow
 * involves user authentication and consent, perfect for web applications with
 * logged-in users.
 * 
 * Authorization Code Flow特点:
 * - User authentication through browser
 * - User consent for requested scopes
 * - Secure token exchange
 * - Refresh token support
 * - Suitable for user-facing applications
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures HTTP security for OAuth2 authorization code flow.
     * This setup:
     * 1. Requires authentication for ALL requests
     * 2. Enables OAuth2 login functionality
     * 3. Redirects unauthenticated users to the authorization server
     * 4. Handles the OAuth2 callback after user authentication
     * 
     * Authorization Code Flow Process:
     * 1. User accesses protected endpoint
     * 2. Spring Security redirects to authorization server (Keycloak)
     * 3. User logs in and grants consent
     * 4. Authorization server redirects back with authorization code
     * 5. Spring exchanges code for access token
     * 6. User is authenticated and can access protected resources
     * 
     * @param http HttpSecurity configuration object
     * @return SecurityFilterChain the configured security filter chain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().authenticated() // All endpoints require authentication
                )
                .oauth2Login(Customizer.withDefaults()); // Enable OAuth2 login

        return http.build();
    }
}
