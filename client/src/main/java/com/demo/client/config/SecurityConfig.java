package com.demo.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security Configuration for OAuth2 Client Application
 * This configures the application to act as an OAuth2 client that can obtain
 * access tokens and make authenticated requests to resource servers.
 */
@Configuration
public class SecurityConfig {

        /**
         * Configures HTTP security for the application.
         * Sets up OAuth2 resource server validation using JWT tokens.
         * This endpoint itself is protected, meaning requests need valid JWT tokens.
         * 
         * @param http HttpSecurity configuration object
         * @return SecurityFilterChain the configured security filter chain
         * @throws Exception if configuration fails
         */
        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) {
                http.authorizeHttpRequests(a -> a.anyRequest().authenticated())
                                .oauth2ResourceServer(oauth2 -> oauth2.jwt(
                                                Customizer.withDefaults()));
                return http.build();
        }

        /**
         * Creates an OAuth2AuthorizedClientService to store authorized clients in
         * memory.
         * This service manages OAuth2 authorized clients (tokens) for the application.
         * 
         * @param clientRegistrationRepository repository containing OAuth2 client
         *                                     registrations
         * @return InMemoryOAuth2AuthorizedClientService instance for managing
         *         authorized clients
         */
        @Bean
        public OAuth2AuthorizedClientService auth2AuthorizedClientService(
                        ClientRegistrationRepository clientRegistrationRepository) {
                return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
        }

        /**
         * Creates an OAuth2AuthorizedClientManager to handle OAuth2 authorization
         * flows.
         * This manager coordinates the process of obtaining access tokens using client
         * credentials.
         * It uses the client credentials flow where the application authenticates
         * itself (not a user).
         * 
         * @param repo          repository containing OAuth2 client registrations
         * @param clientService service for managing authorized clients
         * @return AuthorizedClientServiceOAuth2AuthorizedClientManager configured for
         *         client credentials flow
         */
        @Bean
        public OAuth2AuthorizedClientManager auth2AuthorizedClientManager(
                        ClientRegistrationRepository repo,
                        OAuth2AuthorizedClientService clientService) {
                // Create manager that uses the authorized client service
                var manager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                                repo, clientService);

                // Configure provider to support client credentials grant type
                // This flow is used for machine-to-machine communication
                OAuth2AuthorizedClientProvider provider = OAuth2AuthorizedClientProviderBuilder.builder()
                                .clientCredentials()
                                .build();

                manager.setAuthorizedClientProvider(provider);
                return manager;
        }
}
