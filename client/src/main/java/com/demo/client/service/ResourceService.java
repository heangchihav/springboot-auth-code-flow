package com.demo.client.service;

import com.demo.client.client.ResourceClient;
import org.springframework.stereotype.Service;

/**
 * Service Layer for Resource Operations
 * This service provides business logic for interacting with external resources.
 * It acts as a middle layer between the controller and the HTTP client,
 * allowing for additional business logic, validation, or transformation if
 * needed.
 */
@Service
public class ResourceService {

    private final ResourceClient resourceClient;

    /**
     * Constructor injection of ResourceClient.
     * This service delegates the actual HTTP operations to the ResourceClient.
     * 
     * @param resourceClient HTTP client for making authenticated requests
     */
    public ResourceService(ResourceClient resourceClient) {
        this.resourceClient = resourceClient;
    }

    /**
     * Fetches data from external resource server.
     * This method demonstrates the service layer pattern where business logic
     * can be added here (validation, transformation, caching, etc.) before
     * or after calling the HTTP client.
     * 
     * Currently, it simply delegates to ResourceClient, but could be extended
     * to include:
     * - Data validation
     * - Response transformation
     * - Caching logic
     * - Error handling and retry logic
     * - Logging and monitoring
     * 
     * @return String response from the resource server
     */
    public String fetchDataFromResource() {
        return resourceClient.fetchDataWithClientCredentials();
    }

    /**
     * Alternative method to fetch data using an existing user's token.
     * This method demonstrates how to use a user's already authenticated token
     * instead of performing client credentials authentication again.
     * 
     * Use cases:
     * - When user is already logged into the system
     * - To avoid double authentication (authorization code + client credentials)
     * - When you have access to the user's OAuth2AuthenticationToken
     * - In microservices where authentication context is passed between services
     * 
     * This approach is more efficient because:
     * - No additional token request to the authorization server
     * - Faster response time
     * - Uses the user's existing session/token
     * - Avoids unnecessary authentication round-trips
     * 
     * @return String response from the resource server
     */
    public String fetchDataFromResourceWithToken() {
        return resourceClient.fetchDataWithExistingToken();
    }
}
