package com.demo.pure_client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.*;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.client.RestTemplate;

/**
 * OAuth2 Configuration for Pure Client Application
 * This configures OAuth2 client credentials flow for a command-line
 * application.
 * Unlike the web client, this app runs once and exits after making the request.
 * 
 * This configuration is identical to the web client's OAuth2 setup,
 * demonstrating that the same OAuth2 flow works for different application
 * types.
 */
@Configuration
public class OAuth2Config {

    /**
     * Creates a RestTemplate bean for making HTTP requests.
     * RestTemplate is used to make the actual HTTP request to the resource server
     * after obtaining the OAuth2 access token.
     * 
     * @return RestTemplate instance for HTTP operations
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Creates an OAuth2AuthorizedClientService to store authorized clients in
     * memory.
     * This service manages OAuth2 authorized clients (tokens) for the application.
     * In a command-line app, this stores the token temporarily during execution.
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
     * 
     * The client credentials flow is perfect for command-line applications because:
     * - No user interaction required (no browser needed)
     * - Application authenticates using client ID and secret
     * - Suitable for automated scripts and backend services
     * - Fast and efficient for machine-to-machine communication
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
