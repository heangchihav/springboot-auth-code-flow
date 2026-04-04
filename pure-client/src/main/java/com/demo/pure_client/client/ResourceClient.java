package com.demo.pure_client.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * HTTP Client for OAuth2 Resource Server Communication
 * This class is identical to the web client's ResourceClient, demonstrating
 * that
 * the same OAuth2 client credentials flow works in both web and command-line
 * applications.
 * 
 * In a command-line context, this client makes a single request and then the
 * application exits, making it perfect for scripts, cron jobs, and automation.
 */
@Service
public class ResourceClient {

    private final RestTemplate restTemplate;
    private final OAuth2AuthorizedClientManager authorizedClientManager;

    // Resource server URL injected from application.properties
    @Value("${service2.url}")
    private String resourceServiceUrl;

    /**
     * Constructor injection of dependencies.
     * 
     * @param restTemplate            HTTP client for making requests
     * @param authorizedClientManager OAuth2 manager for handling token acquisition
     */
    public ResourceClient(RestTemplate restTemplate, OAuth2AuthorizedClientManager authorizedClientManager) {
        this.restTemplate = restTemplate;
        this.authorizedClientManager = authorizedClientManager;
    }

    /**
     * Fetches data from the resource server using OAuth2 client credentials flow.
     * This method demonstrates the complete OAuth2 client credentials flow in a
     * command-line context:
     * 
     * Command-line specific considerations:
     * - No user interaction required (perfect for automation)
     * - Application authenticates using client ID and secret from properties
     * - Token is obtained and used in the same execution
     * - No session management needed (stateless)
     * 
     * OAuth2 Flow Steps:
     * 1. Create OAuth2 authorize request with client registration ID
     * 2. Use authorized client manager to obtain access token from Keycloak
     * 3. Extract access token from the authorized client response
     * 4. Set Bearer token in HTTP Authorization header
     * 5. Make authenticated GET request to resource server
     * 6. Return response body (typically printed to console)
     * 
     * Use cases for this pattern:
     * - Automated data fetching scripts
     * - Cron jobs and scheduled tasks
     * - Backend service integration
     * - CI/CD pipeline operations
     * - Microservice communication
     * 
     * @return String response from the resource server
     * @throws Exception if token acquisition or HTTP request fails
     */
    public String fetchData() {
        // Step 1: Create OAuth2 authorize request for client credentials flow
        var authRequest = OAuth2AuthorizeRequest
                .withClientRegistrationId("keycloak-client")
                .principal("machine") // Represents the client application
                .build();

        // Step 2: Obtain access token from authorization server
        var authorizedClient = authorizedClientManager.authorize(authRequest);
        String accessToken = authorizedClient.getAccessToken().getTokenValue();

        // Step 3: Prepare HTTP headers with Bearer token
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Sets Authorization: Bearer <token>

        // Step 4: Make authenticated request to resource server
        var response = restTemplate.exchange(resourceServiceUrl + "/data",
                HttpMethod.GET,
                new HttpEntity<>(headers), // Entity with headers, no body for GET
                String.class);

        // Step 5: Return response body
        return response.getBody();
    }
}
