package com.demo.client.controller;

import com.demo.client.service.ResourceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for Proxy Endpoints
 * This controller provides HTTP endpoints that proxy requests to the resource
 * server.
 * It demonstrates how a client application can authenticate and fetch data from
 * a protected service.
 */
@RestController
public class ProxyController {

    private final ResourceService resourceService;

    /**
     * Constructor injection of ResourceService.
     * Spring automatically injects the ResourceService bean.
     * 
     * @param resourceService service for handling resource operations
     */
    public ProxyController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    /**
     * Proxy endpoint that fetches data from resource server.
     * This endpoint demonstrates OAuth2 client credentials flow:
     * 1. Client authenticates using client credentials
     * 2. Obtains access token from authorization server
     * 3. Makes authenticated request to resource server
     * 4. Returns response to caller
     * 
     * @return String response from resource server
     */
    @GetMapping("/proxy")
    public String proxy() {
        return resourceService.fetchDataFromResource();
    }

    /**
     * Alternative endpoint that uses an existing authenticated user's token.
     * This endpoint demonstrates how to reuse an already authenticated user's token
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
    @GetMapping("/proxy-with-user-token")
    public String proxyWithUserToken() {
        // Use the alternative method that automatically gets the existing token
        return resourceService.fetchDataFromResourceWithToken();
    }
}
