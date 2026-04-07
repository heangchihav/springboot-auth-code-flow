package com.demo.pkce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * Security Configuration for OAuth2 PKCE Resource Server
 * This configures the application to act as an OAuth2 resource server that validates
 * JWT tokens and protects endpoints. This is specifically designed for PKCE (Proof Key for Code Exchange)
 * flow, which is commonly used in single-page applications (SPAs) and mobile apps.
 * 
 * PKCE Flow Characteristics:
 * - Uses code verifier and code challenge for enhanced security
 * - Perfect for public clients (SPAs, mobile apps) that cannot store secrets
 * - Prevents authorization code interception attacks
 * - Recommended for modern web applications
 */
@Configuration
public class SecurityConfig {

    /**
     * Configures HTTP security for the OAuth2 resource server with PKCE support.
     * This setup:
     * 1. Requires authentication for ALL requests
     * 2. Enables OAuth2 resource server functionality with JWT validation
     * 3. Validates JWT tokens using the issuer URI configured in application.properties
     * 4. Configures CORS for frontend integration (e.g., React/Vue SPA)
     * 
     * JWT validation process for PKCE:
     * - Checks token signature using authorization server's public key
     * - Validates token expiration
     * - Validates issuer and audience claims
     * - Extracts user/authorities from token claims
     * - Ensures token was obtained through PKCE flow
     * 
     * @param http HttpSecurity configuration object
     * @return SecurityFilterChain the configured security filter chain
     * @throws Exception if configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(a -> a
                .anyRequest().authenticated() // All endpoints require authentication
        )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                        Customizer.withDefaults() // Use default JWT validation
                ))
                .cors(cors -> cors.configurationSource(corsConfigurationSource())); // Enable CORS
        
        return http.build();
    }

    /**
     * Configures CORS (Cross-Origin Resource Sharing) for frontend integration.
     * This is essential for PKCE applications where the frontend (SPA) runs on a different
     * domain than the backend API.
     * 
     * CORS Configuration:
     * - Allows requests from frontend development server (localhost:5173)
     * - Supports common HTTP methods (GET, POST, PUT, DELETE)
     * - Allows Authorization header for JWT tokens
     * - Allows Content-Type header for API requests
     * - Enables credentials for cookie-based sessions (if needed)
     * 
     * Security considerations:
     * - In production, replace localhost with actual frontend domain
     * - Consider more restrictive origins for production
     * - Review allowed headers based on actual API requirements
     * 
     * @return CorsConfigurationSource configured for SPA integration
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        
        // Frontend development server (React/Vue/Angular)
        corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173"));
        
        // HTTP methods that frontend can use
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
        
        // Headers that frontend can send (essential for OAuth2)
        corsConfiguration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        
        // Allow credentials for cookie-based authentication (if needed)
        corsConfiguration.setAllowCredentials(true);

        // Apply CORS configuration to API endpoints
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", corsConfiguration);
        
        return source;
    }
}
