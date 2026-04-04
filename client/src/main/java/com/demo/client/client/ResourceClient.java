package com.demo.client.client;

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
 * This class handles the OAuth2 client credentials flow and makes authenticated
 * requests to the resource server. It demonstrates machine-to-machine
 * authentication.
 * 
 * The client credentials flow is used when the application itself needs to
 * authenticate
 * (not a user), typically for backend services calling other backend services.
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
     * This method demonstrates the complete OAuth2 client credentials flow:
     * 
     * 1. Create an OAuth2 authorize request with client registration ID
     * 2. Use the authorized client manager to obtain an access token
     * 3. Extract the access token from the authorized client
     * 4. Set the Bearer token in the HTTP headers
     * 5. Make an authenticated request to the resource server
     * 6. Return the response body
     * 
     * The client credentials flow involves:
     * - No user interaction required
     * - Application authenticates using client ID and secret
     * - Suitable for machine-to-machine communication
     * - Access tokens are typically short-lived
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
