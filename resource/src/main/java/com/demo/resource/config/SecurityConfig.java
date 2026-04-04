package com.demo.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration for OAuth2 Resource Server
 * This configures the application to act as an OAuth2 resource server that
 * validates
 * JWT tokens and protects endpoints. Only requests with valid JWT tokens are
 * allowed.
 * 
 * Resource servers are APIs that are protected by OAuth2 and require valid
 * access tokens.
 */
@Configuration
public class SecurityConfig {

        /**
         * Configures HTTP security for the OAuth2 resource server.
         * This setup:
         * 1. Requires authentication for ALL requests (no public endpoints)
         * 2. Enables OAuth2 resource server functionality with JWT validation
         * 3. Validates JWT tokens using the issuer URI configured in
         * application.properties
         * 
         * JWT validation process:
         * - Checks token signature using authorization server's public key
         * - Validates token expiration
         * - Validates issuer and audience claims
         * - Extracts user/authorities from token claims
         * 
         * @param http HttpSecurity configuration object
         * @return SecurityFilterChain the configured security filter chain
         * @throws Exception if configuration fails
         */
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) {
                http.authorizeHttpRequests(a -> a
                                .anyRequest().authenticated() // All endpoints require authentication
                )
                                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                                                Customizer.withDefaults() // Use default JWT validation
                                ));
                return http.build();
        }
}
